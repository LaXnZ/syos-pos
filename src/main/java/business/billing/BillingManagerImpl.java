package business.billing;

import entities.models.*;
import entities.repositories.*;
import utils.BillFormatter;
import utils.DiscountCalculator;
import utils.TaxCalculator;
import strategies.payment.CashPayment;
import strategies.payment.PaymentHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BillingManagerImpl implements BillingManager {

    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final ItemRepository itemRepository;
    private final DiscountCalculator discountCalculator;
    private final TaxCalculator taxCalculator;
    private final PaymentHandler paymentHandler;
    private final ShelfRepository shelfRepository;
    private final StoreInventoryRepository storeInventoryRepository;


    public BillingManagerImpl(BillRepository billRepository,
                              CustomerRepository customerRepository,
                              TransactionRepository transactionRepository,
                              ItemRepository itemRepository,
                              ShelfRepository shelfRepository,
                              StoreInventoryRepository storeInventoryRepository) {
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.itemRepository = itemRepository;
        this.discountCalculator = new DiscountCalculator();
        this.taxCalculator = new TaxCalculator();
        this.paymentHandler = new PaymentHandler();
        this.shelfRepository = shelfRepository;
        this.storeInventoryRepository = storeInventoryRepository;
    }

    @Override
    public Bill createBill(Customer customer) {
        Bill bill = new Bill(LocalDate.now(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);
        billRepository.save(bill);
        return bill;
    }

    @Override
    public void addItemToBill(Bill bill, Item item, int quantity) {
        if (item != null && item.getItemId() > 0) {
            BigDecimal itemPrice = item.getItemPrice().multiply(BigDecimal.valueOf(quantity));
            bill.setTotalPrice(bill.getTotalPrice().add(itemPrice));

            deductStock(item.getItemCode(), quantity);

            // Log the item added to the bill
            System.out.println("Item added to the bill: " + item.getItemName() + " - Quantity: " + quantity);

            // Save the transaction with the proper item reference
            Transaction transaction = new Transaction(bill, item, quantity, itemPrice, LocalDate.now(), "purchase");
            transactionRepository.save(transaction);

            // Save the updated bill
            billRepository.save(bill);
        } else {
            System.out.println("Error: Item is invalid or not found in the database.");
        }
    }

    @Override
    public void applyDiscount(Bill bill, double discountRate) {
        BigDecimal discountAmount = bill.getTotalPrice().multiply(BigDecimal.valueOf(discountRate/100));
        bill.setDiscountAmount(discountAmount);

        // Recalculate the final price after discount
        BigDecimal finalPrice = bill.getTotalPrice().subtract(discountAmount).add(bill.getTaxAmount());
        bill.setFinalPrice(finalPrice);

        System.out.println("Discount applied to the bill.");
        billRepository.save(bill);
    }

    @Override
    public void finalizeBill(Bill bill, double cashTendered, boolean useLoyaltyPoints) {
        // Step 1: Apply tax to the bill
        BigDecimal taxAmount = BigDecimal.valueOf(taxCalculator.calculateTax(bill.getTotalPrice().doubleValue()));
        bill.setTaxAmount(taxAmount);

        // Step 2: Calculate the final price after applying any discounts
        BigDecimal finalPrice = bill.getTotalPrice().add(taxAmount).subtract(bill.getDiscountAmount());

        // Step 3: Apply loyalty points if the customer chooses to use them
        if (useLoyaltyPoints && bill.getCustomer().getLoyaltyPoints() > 0) {
            BigDecimal loyaltyPointsValue = BigDecimal.valueOf(bill.getCustomer().getLoyaltyPoints());
            finalPrice = finalPrice.subtract(loyaltyPointsValue);
            bill.getCustomer().setLoyaltyPoints(0); // Reset loyalty points
        }

        // Step 4: Ensure final price is not negative
        bill.setFinalPrice(finalPrice.compareTo(BigDecimal.ZERO) > 0 ? finalPrice : BigDecimal.ZERO);

        // Step 5: Set the cash tendered and calculate change
        bill.setCashTendered(BigDecimal.valueOf(cashTendered));
        BigDecimal change = BigDecimal.valueOf(cashTendered).subtract(bill.getFinalPrice());
        bill.setChangeAmount(change);

        // Step 6: Update loyalty points based on the final price
        int loyaltyPointsEarned = bill.getFinalPrice().multiply(BigDecimal.valueOf(0.05)).intValue(); // 5% loyalty points
        bill.getCustomer().setLoyaltyPoints(loyaltyPointsEarned);

        // Save the updated customer and bill
        customerRepository.update(bill.getCustomer());
        billRepository.save(bill);

        // Step 7: Display the final bill
        displayBill(bill);
    }

    private void displayBill(Bill bill) {
        List<Transaction> transactions = transactionRepository.findByBillId(bill.getBillId());
        String formattedBill = BillFormatter.formatBill(bill, bill.getCustomer(), transactions);
        System.out.println(formattedBill);
    }

    @Override
    public Bill getBillById(int billId) {
        return billRepository.findById(billId);
    }

    @Override
    public List<Transaction> getTransactionsByBillId(int billId) {
        return transactionRepository.findByBillId(billId);
    }

    @Override
    public void removeItemFromBill(Bill bill, Item item) {
        if (item != null && item.getItemId() > 0) {
            BigDecimal itemPrice = item.getItemPrice();
            bill.setTotalPrice(bill.getTotalPrice().subtract(itemPrice));
            System.out.println("Item removed from the bill: " + item.getItemName());
            billRepository.save(bill);
        } else {
            System.out.println("Error: Item is invalid or not found in the database.");
        }
    }

    private void deductStock(String itemCode, int quantity) {
        // Step 1: Deduct from shelf first
        Shelf shelf = shelfRepository.findByItemCode(itemCode);
        if (shelf != null && shelf.getQuantity() >= quantity) {
            shelf.setQuantity(shelf.getQuantity() - quantity);
            shelfRepository.update(shelf);
        } else {
            // Step 2: If not enough on the shelf, take the remaining from inventory
            if (shelf != null) {
                quantity -= shelf.getQuantity();
                shelf.setQuantity(0);
                shelfRepository.update(shelf);
            }
            deductFromInventory(itemCode, quantity);
        }
    }

    private void deductFromInventory(String itemCode, int quantity) {
        List<StoreInventory> inventoryList = storeInventoryRepository.findByItemCodeOrderedByExpiry(itemCode);

        for (StoreInventory inventory : inventoryList) {
            if (inventory.getQuantityInStock() >= quantity) {
                inventory.setQuantityInStock(inventory.getQuantityInStock() - quantity);
                storeInventoryRepository.update(inventory);
                reshelve(itemCode, quantity);  // Reshelve the remaining quantity
                break;
            } else {
                quantity -= inventory.getQuantityInStock();
                inventory.setQuantityInStock(0);
                storeInventoryRepository.update(inventory);
            }
        }
    }

    private void reshelve(String itemCode, int quantity) {
        Shelf shelf = shelfRepository.findByItemCode(itemCode);
        if (shelf != null) {
            shelf.setQuantity(shelf.getQuantity() + quantity);
            shelfRepository.update(shelf);
        } else {
            // Create a new batch code for the new shelf entry (you can implement your own logic to generate batch codes)
            String batchCode = generateBatchCode(itemCode);  // Assuming a method exists to generate batch codes

            // Create new shelf entry if not exists
            Shelf newShelf = new Shelf(itemCode, quantity, LocalDate.now(), LocalDate.now().plusDays(30), batchCode);  // Example expiry
            shelfRepository.save(newShelf);
        }
        System.out.println("Items reshelved for item code: " + itemCode);
    }

    // You can create a method like this to generate a unique batch code
    private String generateBatchCode(String itemCode) {
        return itemCode + "-" + LocalDate.now().toString();  // Example: ItemCode-YYYY-MM-DD
    }

}
