package core;

import business.billing.BillingManager;
import business.billing.BillingManagerImpl;
import business.customer.CustomerManager;
import business.customer.CustomerManagerImpl;
import business.item.ItemManager;
import business.item.ItemManagerImpl;
import business.reporting.ReportingManager;
import business.reporting.ReportingManagerImpl;
import entities.repositories.ReportingRepositoryImpl;
import entities.repositories.CustomerRepositoryImpl;
import business.stock.StockManager;
import entities.repositories.StockRepositoryImpl;
import business.stock.StockManagerImpl;
import entities.repositories.ItemRepositoryImpl;
import database.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
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
            StockRepositoryImpl stockRepository = new StockRepositoryImpl(connection); // Stock Repository
            ReportingRepositoryImpl reportingRepository = new ReportingRepositoryImpl(connection);


            // Initialize Managers (pass the correct repositories)
            BillingManager billingManager = new BillingManagerImpl(connection);
            CustomerManager customerManager = new CustomerManagerImpl(customerRepository);  // Fix here
            ItemManager itemManager = new ItemManagerImpl(itemRepository);  // Fix here
            ReportingManager reportingManager = new ReportingManagerImpl(reportingRepository);
            StockManager stockManager = new StockManagerImpl(stockRepository); // Stock Manager


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
                        StockManagementCLI.handleStockManagement(stockManager, scanner);  // Invoke Stock Management CLI
                        break;
                    case 5:
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

    // Billing Use Case
    private static void handleBilling(BillingManager billingManager, ItemManager itemManager, Scanner scanner) {
        // Billing logic (same as before)
    }

    // Customer Management Use Case
    private static void handleCustomerManagement(CustomerManager customerManager, Scanner scanner) {
        // Customer management logic (same as before)
    }

    // Item Management Use Case
    private static void handleItemManagement(ItemManager itemManager, Scanner scanner) {
        // Item management logic (same as before)
    }

    // Stock Management Use Case
    private static void handleStockManagement(ItemManager itemManager, Scanner scanner) {
        // Stock management logic (same as before)
    }

    // Reporting Use Case
    private static void handleReporting(ReportingManager reportingManager, Scanner scanner) {
        // Reporting logic (same as before)
    }
}
