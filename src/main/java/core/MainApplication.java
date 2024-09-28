package core;

import business.billing.BillingManager;
import business.billing.BillingManagerImpl;
import business.billing.PaymentProcessor;
import database.DBConnection;
import database.DBInitializer;
import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;
import entities.repositories.CustomerRepository;
import entities.repositories.CustomerRepositoryImpl;
import entities.repositories.ItemRepository;
import entities.repositories.ItemRepositoryImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;

public class MainApplication {
    public static void main(String[] args) {
        System.out.println("Starting the SYOS POS application...");

        // Initialize the database
        DBConnection dbConnectionManager = new DBConnection();

        // Run database initialization before proceeding with other operations
        DBInitializer dbInitializer = new DBInitializer(dbConnectionManager);
        dbInitializer.initializeDatabase();  // Initialize tables if not exist

        // Use try-catch to ensure the connection is handled properly
        try (Connection connection = dbConnectionManager.getConnection()) {
            // Pass the same connection to repositories
            CustomerRepository customerRepo = new CustomerRepositoryImpl(connection);
            ItemRepository itemRepo = new ItemRepositoryImpl(connection);
            BillingManager billingManager = new BillingManagerImpl(connection);

            // Create a new customer
            Customer customer = new Customer("John Doe", "0123456789", "john@example.com", LocalDate.now());
            customerRepo.save(customer);  // Now the customerId will be set

            // Create items
            Item item1 = new Item("SP-001", "Soap", BigDecimal.valueOf(2.50));
            Item item2 = new Item("SP-002", "Shampoo", BigDecimal.valueOf(5.00));
            itemRepo.save(item1);
            itemRepo.save(item2);

            // Create a new bill for the customer
            Bill bill = billingManager.createBill(customer);
            billingManager.addItemToBill(bill, item1, 2); // Add 2 soaps
            billingManager.addItemToBill(bill, item2, 3); // Add 1 shampoo

            // Apply discount to the bill (10% discount)
            billingManager.applyDiscount(bill, 0.2);

            // Process payment (customer tenders $20)
            PaymentProcessor.processPayment("cash", BigDecimal.valueOf(20), bill);

            // Display the final bill
            System.out.println("Final Bill: ");
            System.out.println("Total Price: " + bill.getTotalPrice());
            System.out.println("Final Price (after discount and tax): " + bill.getFinalPrice());
            System.out.println("Cash Tendered: " + bill.getCashTendered());
            System.out.println("Change: " + bill.getChangeAmount());

        } catch (Exception e) {
            System.out.println("Error in processing: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbConnectionManager.closeConnection(); // Ensure the connection is closed after all operations are done
        }

        System.out.println("Application started.");
    }
}
