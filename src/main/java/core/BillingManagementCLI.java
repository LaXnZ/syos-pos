package core;

import business.billing.BillingManager;
import business.customer.CustomerManager;
import business.item.ItemManager;
import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;
import entities.models.Transaction;
import utils.BillFormatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class BillingManagementCLI {

    public static void handleBilling(BillingManager billingManager, CustomerManager customerManager, ItemManager itemManager, Scanner scanner) {
        // Step 1: Connect to an existing customer or add a new customer if not found
        System.out.println("Billing Section...");
        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();

        Customer customer = customerManager.findCustomerByName(customerName);

        if (customer == null) {
            System.out.println("Customer not found. Adding new customer...");
            // Use Customer Management CLI to add a new customer
            CustomerManagementCLI.addCustomer(customerManager, scanner);
            customer = customerManager.findCustomerByName(customerName); // Re-fetch the newly added customer
        }

        if (customer == null || customer.getCustomerId() == 0) {
            System.out.println("Error: Customer ID is not set or customer not saved properly.");
            return;
        }

        // Step 2: Create a new bill for this customer
        Bill bill = billingManager.createBill(customer);

        boolean addingItems = true;
        while (addingItems) {
            // Step 3: Add items to the bill
            System.out.println("Enter item code:");
            String itemCode = scanner.nextLine();

            Item item = itemManager.findByCode(itemCode);
            if (item != null) {
                System.out.println("Enter quantity:");
                int quantity = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                // Optionally add discount per item
                System.out.println("Enter discount (percentage) for this item or 0 if none:");
                double discountRate = scanner.nextDouble();
                scanner.nextLine();  // Consume newline

                // Add item to the bill and apply discount if any
                billingManager.addItemToBill(bill, item, quantity);
                if (discountRate > 0) {
                    billingManager.applyDiscount(bill, discountRate);
                }

                System.out.println("Item added successfully.");
            } else {
                System.out.println("Item with code " + itemCode + " not found.");
                continue;
            }

            // Step 4: Ask if they want to add more items
            System.out.println("Would you like to add another item? (y/n):");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("y")) {
                addingItems = false;
            }
        }

        // Step 5: Display the total price and loyalty points
        System.out.println("\nTotal Price: " + bill.getTotalPrice());
        System.out.println("Loyalty Points: " + customer.getLoyaltyPoints());
        System.out.println("Would you like to use loyalty points? (y/n):");
        String useLoyalty = scanner.nextLine();

        if (useLoyalty.equalsIgnoreCase("y") && customer.getLoyaltyPoints() > 0) {
            BigDecimal loyaltyPointsValue = BigDecimal.valueOf(customer.getLoyaltyPoints());
            BigDecimal remainingTotal = bill.getTotalPrice().subtract(loyaltyPointsValue);

            // Ensure that loyalty points do not reduce the bill below zero
            if (remainingTotal.compareTo(BigDecimal.ZERO) < 0) {
                remainingTotal = BigDecimal.ZERO;
            }

            bill.setFinalPrice(remainingTotal);
            customer.setLoyaltyPoints(0); // Use all loyalty points
            customerManager.updateCustomer(customer);
            System.out.println("Loyalty points applied. Remaining total: " + remainingTotal);
        } else {
            bill.setFinalPrice(bill.getTotalPrice());
            System.out.println("No loyalty points applied.");
        }

        // Step 6: Accept cash tendered and finalize the bill
        System.out.println("Enter cash tendered:");
        double cashTendered = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        billingManager.finalizeBill(bill, cashTendered);

        // Step 7: Retrieve all transactions for this bill
        List<Transaction> transactions = billingManager.getTransactionsByBillId(bill.getBillId());

        // Step 8: Use BillFormatter to display the formatted bill
        String formattedBill = BillFormatter.formatBill(bill, customer, transactions);
        System.out.println(formattedBill);

        // Step 9: Earn new loyalty points based on 5% of the final price
        BigDecimal newLoyaltyPoints = bill.getFinalPrice().multiply(BigDecimal.valueOf(0.05));
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + newLoyaltyPoints.intValue());
        customerManager.updateCustomer(customer);
        System.out.println("New loyalty points earned: " + newLoyaltyPoints.intValue());
    }
}
