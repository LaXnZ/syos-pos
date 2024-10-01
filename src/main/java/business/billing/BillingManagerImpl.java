package business.billing;

import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;
import entities.models.Transaction;
import entities.repositories.BillRepository;
import entities.repositories.CustomerRepository;
import entities.repositories.TransactionRepository;
import entities.repositories.ItemRepository;
import entities.repositories.BillRepositoryImpl;
import entities.repositories.CustomerRepositoryImpl;
import entities.repositories.TransactionRepositoryImpl;
import entities.repositories.ItemRepositoryImpl;

import java.util.List;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;

public class BillingManagerImpl implements BillingManager {

    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final ItemRepository itemRepository;
    private final Connection connection;

    public BillingManagerImpl(Connection connection) {
        this.connection = connection;
        this.billRepository = new BillRepositoryImpl(connection);
        this.customerRepository = new CustomerRepositoryImpl(connection);
        this.transactionRepository = new TransactionRepositoryImpl(connection);
        this.itemRepository = new ItemRepositoryImpl(connection);
    }

    @Override
    public Bill createBill(Customer customer) {
        if (customer.getCustomerId() == 0) {
            System.out.println("Error: Customer ID is not set or customer not saved properly.");
            return null;  // Or handle this case appropriately
        }
        Bill bill = new Bill(LocalDate.now(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);
        billRepository.save(bill);  // Save bill with the correct customer ID
        return bill;
    }


    @Override
    public void addItemToBill(Bill bill, Item item, int quantity) {
        if (item != null && item.getItemId() > 0) {
            BigDecimal itemPrice = item.getItemPrice().multiply(BigDecimal.valueOf(quantity));
            bill.setTotalPrice(bill.getTotalPrice().add(itemPrice));

            // Create a transaction for this item
            Transaction transaction = new Transaction(bill, item, quantity, itemPrice, LocalDate.now(), "purchase");
            transaction.setItem(item);  // Ensure the item is set in the transaction
            transactionRepository.save(transaction);  // Save transaction to the database

            billRepository.save(bill);  // Save the updated bill
            System.out.println("Item added to the bill: " + item.getItemName() + " - Quantity: " + quantity);
        } else {
            System.out.println("Error: Item is invalid or has not been added to the database.");
        }
    }


    @Override
    public void applyDiscount(Bill bill, double discountRate) {
        // Calculate discount and update the bill
        BigDecimal discountAmount = bill.getTotalPrice().multiply(BigDecimal.valueOf(discountRate / 100));
        bill.setDiscountAmount(bill.getDiscountAmount().add(discountAmount)); // Accumulate discounts
        BigDecimal finalPrice = bill.getTotalPrice().subtract(bill.getDiscountAmount()).add(bill.getTaxAmount());
        bill.setFinalPrice(finalPrice);  // Set final price after discount
        System.out.println("Discount applied to the bill.");

        // Save the updated bill
        billRepository.save(bill);
    }


    @Override
    public void finalizeBill(Bill bill, double cashTendered) {
        // Finalize the bill by calculating the change
        BigDecimal finalPrice = bill.getTotalPrice().subtract(bill.getDiscountAmount()).add(bill.getTaxAmount());
        bill.setFinalPrice(finalPrice);
        bill.setCashTendered(BigDecimal.valueOf(cashTendered));
        BigDecimal changeAmount = BigDecimal.valueOf(cashTendered).subtract(finalPrice);
        bill.setChangeAmount(changeAmount);

        System.out.println("Bill finalized. Change: " + changeAmount);

        // Save the updated bill
        billRepository.save(bill);

        // Update customer's last purchase date and total spent
        Customer customer = bill.getCustomer();
        customer.setLastPurchaseDate(LocalDate.now());  // Update to the current date
        customer.setTotalSpent(customer.getTotalSpent().add(bill.getTotalPrice()));

        // Update the customer in the repository
        customerRepository.update(customer);

        System.out.println("Customer " + customer.getName() + "'s last purchase date and total spent have been updated.");
    }

    @Override
    public List<Transaction> getTransactionsByBillId(int billId) {
        return transactionRepository.findByBillId(billId);
    }


    @Override
    public Bill getBillById(int billId) {
        return billRepository.findById(billId);
    }

    @Override
    public void removeItemFromBill(Bill bill, Item item) {
        if (item != null && item.getItemId() > 0) {
            BigDecimal itemPrice = item.getItemPrice();
            bill.setTotalPrice(bill.getTotalPrice().subtract(itemPrice));
            System.out.println("Item removed from the bill: " + item.getItemName());

            // Save the updated bill
            billRepository.save(bill);
        } else {
            System.out.println("Error: Item is invalid or has not been added to the database.");
        }
    }
}
