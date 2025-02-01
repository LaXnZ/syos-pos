package business.billing;

import entities.models.*;
import entities.repositories.*;
import utils.BillFormatter;
import utils.DiscountCalculator;
import utils.TaxCalculator;
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


            System.out.println("Item added to the bill: " + item.getItemName() + " - Quantity: " + quantity);

            // save the transaction with the bill and item details
            Transaction transaction = new Transaction(bill, item, quantity, itemPrice, LocalDate.now(), "cash");
            transactionRepository.save(transaction);


            billRepository.save(bill);
        } else {
            System.out.println("Error: Item is invalid or not found in the database.");
        }
    }

    @Override
    public void applyDiscount(Bill bill, double discountRate) {
        BigDecimal discountAmount = bill.getTotalPrice().multiply(BigDecimal.valueOf(discountRate/100));
        bill.setDiscountAmount(discountAmount);

        // recalculating the final price after applying the discount
        BigDecimal finalPrice = bill.getTotalPrice().subtract(discountAmount).add(bill.getTaxAmount());
        bill.setFinalPrice(finalPrice);

        System.out.println("Discount applied to the bill.");
        billRepository.save(bill);
    }

    @Override
    public void finalizeBill(Bill bill, double cashTendered, boolean useLoyaltyPoints) {
        // 1 - Calculate the discount
        BigDecimal discountAmount = bill.getTotalPrice().multiply(BigDecimal.valueOf(bill.getDiscountRate() / 100));
        bill.setDiscountAmount(discountAmount);

        // 2 - Calculate the total after discount
        BigDecimal totalAfterDiscount = bill.getTotalPrice().subtract(discountAmount);

        // 3 - Calculate tax on the discounted total
        BigDecimal taxAmount = BigDecimal.valueOf(taxCalculator.calculateTax(totalAfterDiscount.doubleValue()));
        bill.setTaxAmount(taxAmount);

        // 4 - Calculate final price
        BigDecimal finalPrice = totalAfterDiscount.add(taxAmount);

        // 5 - Apply loyalty points if applicable
        if (useLoyaltyPoints && bill.getCustomer().getLoyaltyPoints() > 0) {
            BigDecimal loyaltyPointsValue = BigDecimal.valueOf(bill.getCustomer().getLoyaltyPoints());
            if (loyaltyPointsValue.compareTo(finalPrice) > 0) {
                loyaltyPointsValue = finalPrice; // Cannot apply more points than the total price
            }
            finalPrice = finalPrice.subtract(loyaltyPointsValue);
            bill.getCustomer().setLoyaltyPoints(0); // Reset loyalty points
        }

        // 6 - Set the final price
        bill.setFinalPrice(finalPrice);

        // 7 - Calculate change based on cash tendered
        bill.setCashTendered(BigDecimal.valueOf(cashTendered));
        BigDecimal change = BigDecimal.valueOf(cashTendered).subtract(finalPrice);
        bill.setChangeAmount(change.compareTo(BigDecimal.ZERO) >= 0 ? change : BigDecimal.ZERO);  // No negative change

        // 8 - Update loyalty points (5% of the final price)
        int loyaltyPointsEarned = finalPrice.multiply(BigDecimal.valueOf(0.05)).intValue(); // Earn 5% loyalty points
        bill.getCustomer().setLoyaltyPoints(loyaltyPointsEarned);

        // Save the updated customer and bill
        customerRepository.update(bill.getCustomer());
        billRepository.save(bill);

        // 9 - Display the bill
        displayBill(bill);
    }


    private void displayBill(Bill bill) {
        List<Transaction> transactions = transactionRepository.findByBillId(bill.getBillId());
        String formattedBill = BillFormatter.formatBill(bill, bill.getCustomer(), transactions);
        System.out.println(formattedBill);
    }



    @Override
    public List<Transaction> getTransactionsByBillId(int billId) {
        return transactionRepository.findByBillId(billId);
    }



    private void deductStock(String itemCode, int quantity) {
        // 1 - deduct from shelf stock
        Shelf shelf = shelfRepository.findByItemCode(itemCode);
        if (shelf != null && shelf.getQuantity() >= quantity) {
            shelf.setQuantity(shelf.getQuantity() - quantity);
            shelfRepository.update(shelf);
        } else {
            // 2 - if shelf stock is insufficient, deduct from store inventory
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
                reshelve(itemCode, quantity);
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

            String batchCode = generateBatchCode(itemCode);

            // create a new shelf entry for the item
            Shelf newShelf = new Shelf(itemCode, quantity, LocalDate.now(), LocalDate.now().plusDays(30), batchCode);  // Example expiry
            shelfRepository.save(newShelf);
        }
        System.out.println("Items reshelved for item code: " + itemCode);
    }

    private String generateBatchCode(String itemCode) {
        return itemCode + "-" + LocalDate.now().toString();  // Example: ItemCode-YYYY-MM-DD
    }

}
