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
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BillingManagementCLI {

    public static void handleBilling(BillingManager billingManager, CustomerManager customerManager, ItemManager itemManager, Scanner scanner) {
        System.out.println("Billing Section...");
        System.out.println("Enter customer number:");
        String customerNumber = scanner.nextLine();

        // Find or add customer
        Customer customer = customerManager.findCustomerByPhoneNumber(customerNumber);
        if (customer == null) {
            System.out.println("Customer not found. Adding new customer...");
            CustomerManagementCLI.addCustomer(customerManager, scanner);
            customer = customerManager.findCustomerByPhoneNumber(customerNumber);
        }

        // Create a new bill for the customer
        Bill bill = billingManager.createBill(customer);

        boolean addingItems = true;
        while (addingItems) {
            System.out.println("Enter item code:");
            String itemCode = scanner.nextLine();

            Item item = itemManager.findByCode(itemCode);
            if (item != null) {
                System.out.println("Enter quantity:");
                int quantity = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                System.out.println("Enter discount (percentage) for this item or 0 if none:");
                double discountRate = scanner.nextDouble();
                scanner.nextLine();  // Consume newline

                billingManager.addItemToBill(bill, item, quantity);
                if (discountRate > 0) {
                    billingManager.applyDiscount(bill, discountRate);
                }
                System.out.println("Item added: " + item.getItemName() + " - Quantity: " + quantity);
            } else {
                System.out.println("Item not found.");
            }

            System.out.println("Would you like to add another item? (y/n):");
            addingItems = scanner.nextLine().equalsIgnoreCase("y");
        }

        // Calculate the tax amount (assume 10% tax)
        BigDecimal taxAmount = bill.getTotalPrice().multiply(BigDecimal.valueOf(0.02));
        bill.setTaxAmount(taxAmount);

        // Display total price including tax
        System.out.println("\nTotal Price: " + bill.getTotalPrice());
        System.out.println("Tax Amount: " + taxAmount);
        BigDecimal finalPrice = bill.getTotalPrice().add(taxAmount);
        bill.setFinalPrice(finalPrice);

        // Handle loyalty points
        System.out.println("Loyalty Points: " + customer.getLoyaltyPoints());
        boolean loyaltyPointsApplied = false;
        System.out.println("Would you like to use loyalty points? (y/n):");
        if (scanner.nextLine().equalsIgnoreCase("y") && customer.getLoyaltyPoints() > 0) {
            BigDecimal loyaltyPointsValue = BigDecimal.valueOf(customer.getLoyaltyPoints());
            BigDecimal newTotal = bill.getFinalPrice().subtract(loyaltyPointsValue);
            if (newTotal.compareTo(BigDecimal.ZERO) < 0) {
                newTotal = BigDecimal.ZERO;
            }
            bill.setFinalPrice(newTotal);
            customer.setLoyaltyPoints(0);  // Use all loyalty points
            customerManager.updateCustomer(customer);
            System.out.println("Loyalty points applied.");
            loyaltyPointsApplied = true;
        } else {
            System.out.println("No loyalty points applied.");
        }

        // Display the final price after applying loyalty points
        System.out.println("Final Price after Discount and Tax: " + bill.getFinalPrice());

        // Get cash tendered
        System.out.println("Enter cash tendered:");
        double cashTendered = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        // Finalize the bill and display the change
        billingManager.finalizeBill(bill, cashTendered, loyaltyPointsApplied);

        // Retrieve and display all transactions
        List<Transaction> transactions = billingManager.getTransactionsByBillId(bill.getBillId());
        String formattedBill = BillFormatter.formatBill(bill, customer, transactions);
        System.out.println(formattedBill);

        // Add new loyalty points (5% of the final price)
        BigDecimal newLoyaltyPoints = bill.getFinalPrice().multiply(BigDecimal.valueOf(0.05));
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + newLoyaltyPoints.intValue());
        customerManager.updateCustomer(customer);
        System.out.println("New loyalty points earned: " + newLoyaltyPoints.intValue());
    }
}
