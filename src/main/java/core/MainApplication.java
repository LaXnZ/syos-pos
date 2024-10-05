package core;

import business.billing.BillingManager;
import business.billing.BillingManagerImpl;
import business.customer.CustomerManager;
import business.customer.CustomerManagerImpl;
import business.item.ItemManager;
import business.item.ItemManagerImpl;
import business.reporting.ReportingManager;
import business.reporting.ReportingManagerImpl;
import business.stock.StockManager;
import business.stock.StockManagerImpl;
import entities.repositories.*;
import database.DBConnection;

import java.sql.Connection;
import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        System.out.println("Welcome to SYOS System");

        // Initialize the database connection
        DBConnection dbConnectionManager = new DBConnection();

        try (Connection connection = dbConnectionManager.getConnection()) {
            // Initialize Repositories
            CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl(connection);
            ItemRepositoryImpl itemRepository = new ItemRepositoryImpl(connection);
            TransactionRepositoryImpl transactionRepository = new TransactionRepositoryImpl(connection);
            BillRepositoryImpl billRepository = new BillRepositoryImpl(connection);
            StockRepositoryImpl stockRepository = new StockRepositoryImpl(connection);
            ReportingRepositoryImpl reportingRepository = new ReportingRepositoryImpl(connection);
            ShelfRepositoryImpl shelfRepository = new ShelfRepositoryImpl(connection);
            StoreInventoryRepositoryImpl storeInventoryRepository = new StoreInventoryRepositoryImpl(connection);

            // Initialize Managers
            BillingManager billingManager = new BillingManagerImpl(billRepository, customerRepository, transactionRepository, itemRepository, shelfRepository, storeInventoryRepository);
            CustomerManager customerManager = new CustomerManagerImpl(customerRepository);
            ItemManager itemManager = new ItemManagerImpl(itemRepository);
            StockManager stockManager = new StockManagerImpl(stockRepository);
            ReportingManager reportingManager = new ReportingManagerImpl(reportingRepository);

            boolean running = true;
            Scanner scanner = new Scanner(System.in);

            while (running) {
                System.out.println("\n==== Welcome to SYOS ====");
                System.out.println("1. In-Store POS System");
                System.out.println("2. Online Store");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (option) {
                    case 1:
                        // Redirect to In-Store POS System
                        InStorePOSSystem.startInStoreSystem(billingManager, customerManager, itemManager, stockManager, reportingManager, scanner);
                        break;
                    case 2:
                        // Redirect to Online Store
                        OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);
                        break;
                    case 3:
                        running = false;
                        System.out.println("Exiting SYOS System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option! Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbConnectionManager.closeConnection();
        }
    }
}
