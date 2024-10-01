package core;

import business.reporting.ReportingManager;
import java.util.Scanner;

public class ReportingManagementCLI {

    public static void handleReporting(ReportingManager reportingManager, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n==== Reporting ====");
            System.out.println("1. Total Sales Report");
            System.out.println("2. Reshelving Report");
            System.out.println("3. Reorder Level Report");
            System.out.println("4. Stock Report");
            System.out.println("5. Bill Report");

            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    reportingManager.generateTotalSalesReport();
                    break;
                case 2:
                    reportingManager.generateReshelvingReport();
                    break;
                case 3:
                    reportingManager.generateReorderLevelReport();
                    break;
                case 4:
                    reportingManager.generateStockReport();
                    break;
                case 5:
                    reportingManager.generateBillReport();
                    break;

                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }
}
