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



        Bill bill = billingManager.createBill(customer);

        boolean addingItems = true;
        while (addingItems) {
            System.out.println("Enter item code:");
            String itemCode = scanner.nextLine();

            Item item = itemManager.findByCode(itemCode);
            if (item != null) {
                System.out.println("Enter quantity:");
                int quantity = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Enter discount (percentage) for this item or 0 if none:");
                double discountRate = scanner.nextDouble();
                scanner.nextLine();

                billingManager.addItemToBill(bill, item, quantity);
                if (discountRate > 0) {
                    billingManager.applyDiscount(bill, discountRate);
                }
                System.out.println("Item added successfully.");
            } else {
                System.out.println("Item not found.");
            }

            System.out.println("Would you like to add another item? (y/n):");
            addingItems = scanner.nextLine().equalsIgnoreCase("y");
        }

        // Display total and handle loyalty points
        System.out.println("\nTotal Price: " + bill.getTotalPrice());
        System.out.println("Loyalty Points: " + customer.getLoyaltyPoints());
        System.out.println("Would you like to use loyalty points? (y/n):");
        if (scanner.nextLine().equalsIgnoreCase("y") && customer.getLoyaltyPoints() > 0) {
            BigDecimal loyaltyPointsValue = BigDecimal.valueOf(customer.getLoyaltyPoints());
            BigDecimal newTotal = bill.getTotalPrice().subtract(loyaltyPointsValue);
            if (newTotal.compareTo(BigDecimal.ZERO) < 0) {
                newTotal = BigDecimal.ZERO;
            }
            bill.setFinalPrice(newTotal);
            customer.setLoyaltyPoints(0);  // Use all loyalty points
            customerManager.updateCustomer(customer);
            System.out.println("Loyalty points applied.");
        } else {
            bill.setFinalPrice(bill.getTotalPrice());
            System.out.println("No loyalty points applied.");
        }

        System.out.println("Enter cash tendered:");
        double cashTendered = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        billingManager.finalizeBill(bill, cashTendered);

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
