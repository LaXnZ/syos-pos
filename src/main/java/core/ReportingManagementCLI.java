package core;

import business.reporting.ReportingManager;
import java.time.LocalDate;
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
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    LocalDate salesDate = getDateFromUser(scanner);
                    reportingManager.generateTotalSalesReport(salesDate);
                    break;
                case 2:
                    LocalDate reshelvingDate = getDateFromUser(scanner);
                    reportingManager.generateReshelvingReport(reshelvingDate);
                    break;
                case 3:
                    LocalDate reorderDate = getDateFromUser(scanner);
                    reportingManager.generateReorderLevelReport(reorderDate);
                    break;
                case 4:
                    LocalDate stockDate = getDateFromUser(scanner);
                    reportingManager.generateStockReport(stockDate);
                    break;
                case 5:
                    LocalDate billDate = getDateFromUser(scanner);
                    reportingManager.generateBillReport(billDate);
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

    private static LocalDate getDateFromUser(Scanner scanner) {
        System.out.print("Enter the date for the report (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        return LocalDate.parse(dateInput);  // You can add error handling for parsing
    }
}
