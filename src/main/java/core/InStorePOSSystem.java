package core;

import business.billing.BillingManager;
import business.customer.CustomerManager;
import business.item.ItemManager;
import business.reporting.ReportingManager;
import business.stock.StockManager;

import java.util.Scanner;

public class InStorePOSSystem {

    public static void startInStoreSystem(BillingManager billingManager, CustomerManager customerManager, ItemManager itemManager,
                                          StockManager stockManager, ReportingManager reportingManager, Scanner scanner) {
        boolean running = true;

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
                    ReportingManagementCLI.handleReporting(reportingManager, scanner);
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting POS System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option! Please choose a valid one.");
            }
        }
    }
}
