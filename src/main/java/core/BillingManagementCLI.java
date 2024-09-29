package core;

import business.billing.BillingManager;
import business.item.ItemManager;
import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class BillingManagementCLI {

    public static void handleBilling(BillingManager billingManager, ItemManager itemManager, Scanner scanner) {
        System.out.println("Billing Section...");

        // Create a new customer
        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();
        Customer customer = new Customer(customerName, "0123456789", customerName + "@example.com", LocalDate.now());

        // Create a bill for this customer
        Bill bill = billingManager.createBill(customer);

        // Add items to the bill
        String continueAdding = "y";
        while (continueAdding.equalsIgnoreCase("y")) {
            System.out.println("Enter item code:");
            String itemCode = scanner.nextLine();

            Item item = itemManager.findByCode(itemCode);
            if (item != null) {
                System.out.println("Enter quantity:");
                int quantity = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                billingManager.addItemToBill(bill, item, quantity);
                System.out.println("Item added to the bill successfully.");
            } else {
                System.out.println("Item with code " + itemCode + " not found.");
            }

            System.out.println("Would you like to add another item? (y/n):");
            continueAdding = scanner.nextLine();
        }

        // Finalize the bill
        System.out.println("Enter cash tendered:");
        double cashTendered = scanner.nextDouble();
        billingManager.finalizeBill(bill, cashTendered);

        // Display the final bill
        System.out.println("\n==== Final Bill ====");
        System.out.println("Total Price: " + bill.getTotalPrice());
        System.out.println("Final Price (after discount and tax): " + bill.getFinalPrice());
        System.out.println("Cash Tendered: " + bill.getCashTendered());
        System.out.println("Change: " + bill.getChangeAmount());
    }
}
