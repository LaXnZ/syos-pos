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
        System.out.println("Starting SYOS POS System...");

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
            ShelfRepositoryImpl shelfRepository = new ShelfRepositoryImpl(connection); // New repository
            StoreInventoryRepositoryImpl storeInventoryRepository = new StoreInventoryRepositoryImpl(connection); // New repository


            // Initialize Managers (pass the correct repositories)
            BillingManager billingManager = new BillingManagerImpl(billRepository, customerRepository, transactionRepository, itemRepository, shelfRepository,
                    storeInventoryRepository   );
            CustomerManager customerManager = new CustomerManagerImpl(customerRepository);
            ItemManager itemManager = new ItemManagerImpl(itemRepository);
            StockManager stockManager = new StockManagerImpl(stockRepository);
            ReportingManager reportingManager = new ReportingManagerImpl(reportingRepository); // Updated line

            // Main Menu
            boolean running = true;
            Scanner scanner = new Scanner(System.in);

            while (running) {
                System.out.println("\n==== SYOS POS System ====");
                System.out.println("1. Billing");
                System.out.println("2. Customer Management");
                System.out.println("3. Item Management");
                System.out.println("4. Stock Management");
                System.out.println("5. Reporting");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (option) {
                    case 1:
                        BillingManagementCLI.handleBilling(billingManager, customerManager, itemManager, scanner);
                        break;
                    case 2:
                        CustomerManagementCLI.handleCustomerManagement(customerManager, scanner);
                        break;
                    case 3:
                        ItemManagementCLI.handleItemManagement(itemManager, scanner);
                        break;
                    case 4:
                        StockManagementCLI.handleStockManagement(stockManager, scanner);
                        break;
                    case 5:
                        // Use the ReportingManager for reporting tasks
                        ReportingManagementCLI.handleReporting(reportingManager, scanner);
                        break;
                    case 6:
                        running = false;
                        System.out.println("Exiting SYOS POS System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option! Please choose a number between 1 and 6.");
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
