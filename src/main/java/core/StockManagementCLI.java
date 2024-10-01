package core;

import business.stock.StockManager;
import entities.models.Stock;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class StockManagementCLI {

    public static void handleStockManagement(StockManager stockManager, Scanner scanner) {
        boolean running = true;

        // Menu-driven approach for Stock Management
        while (running) {
            System.out.println("\n==== Stock Management ====");
            System.out.println("1. Add Stock");
            System.out.println("2. View All Stock");
            System.out.println("3. Update Stock");
            System.out.println("4. Remove Stock");
            System.out.println("5. Reshelve Stock");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    // Add Stock
                    try {
                        System.out.println("Enter batch code:");
                        String batchCode = scanner.nextLine();

                        System.out.println("Enter item code:");
                        String itemCode = scanner.nextLine();

                        System.out.println("Enter quantity in stock:");
                        int quantity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        System.out.println("Enter shelf capacity (for reshelving):");
                        int shelfCapacity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        System.out.println("Enter date of purchase (YYYY-MM-DD):");
                        String purchaseDateStr = scanner.nextLine();
                        LocalDate purchaseDate = LocalDate.parse(purchaseDateStr);

                        System.out.println("Enter expiry date (YYYY-MM-DD):");
                        String expiryDateStr = scanner.nextLine();
                        LocalDate expiryDate = LocalDate.parse(expiryDateStr);

                        // New missing argument (example: stockLocation)
                        System.out.println("Enter stock location:");
                        String stockLocation = scanner.nextLine();

                        Stock newStock = new Stock(batchCode, itemCode, quantity, purchaseDate, expiryDate, 0, shelfCapacity, stockLocation);

                        stockManager.addStock(newStock);
                        System.out.println("Stock added successfully!");

                    } catch (DateTimeParseException e) {
                        System.out.println("Error: Invalid date format. Please use YYYY-MM-DD.");
                    } catch (Exception e) {
                        System.out.println("Error adding stock: " + e.getMessage());
                    }
                    break;

                case 2:
                    // View All Stock
                    List<Stock> allStock = stockManager.findAll();
                    if (allStock.isEmpty()) {
                        System.out.println("No stock available.");
                    } else {
                        allStock.forEach(System.out::println);
                    }
                    break;

                case 3:
                    // Update Stock
                    System.out.println("Enter batch code to update:");
                    String updateBatchCode = scanner.nextLine();
                    Stock stockToUpdate = stockManager.findByBatchCode(updateBatchCode);

                    if (stockToUpdate != null) {
                        try {
                            System.out.println("Enter new quantity in stock:");
                            int updatedQuantity = scanner.nextInt();
                            scanner.nextLine();  // Consume newline

                            System.out.println("Enter new shelf capacity:");
                            int updatedShelfCapacity = scanner.nextInt();
                            scanner.nextLine();  // Consume newline

                            System.out.println("Enter new expiry date (YYYY-MM-DD):");
                            String updatedExpiryDateStr = scanner.nextLine();
                            LocalDate updatedExpiryDate = LocalDate.parse(updatedExpiryDateStr);

                            System.out.println("Enter new stock location:");
                            String updatedStockLocation = scanner.nextLine();

                            stockToUpdate.setQuantityInStock(updatedQuantity);
                            stockToUpdate.setShelfCapacity(updatedShelfCapacity);
                            stockToUpdate.setExpiryDate(updatedExpiryDate);
                            stockToUpdate.setStockLocation(updatedStockLocation);

                            stockManager.updateStock(stockToUpdate);
                            System.out.println("Stock updated successfully!");
                        } catch (DateTimeParseException e) {
                            System.out.println("Error: Invalid date format. Please use YYYY-MM-DD.");
                        } catch (Exception e) {
                            System.out.println("Error updating stock: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Stock not found with batch code: " + updateBatchCode);
                    }
                    break;

                case 4:
                    // Remove Stock
                    System.out.println("Enter batch code to remove:");
                    String removeBatchCode = scanner.nextLine();
                    stockManager.removeStock(removeBatchCode);
                    System.out.println("Stock removed successfully!");
                    break;

                case 5:
                    // Reshelve Stock (Prioritize FIFO based on expiry dates)
                    List<Stock> stockToReshelve = stockManager.reshelveStock();
                    if (stockToReshelve.isEmpty()) {
                        System.out.println("No stock to reshelve.");
                    } else {
                        System.out.println("Stock reshelved successfully!");
                        stockToReshelve.forEach(System.out::println);
                    }
                    break;

                case 6:
                    // Back to Main Menu
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }
}
