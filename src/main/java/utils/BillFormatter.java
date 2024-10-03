package utils;

import entities.models.Bill;
import entities.models.Customer;
import entities.models.Transaction;

import java.util.List;

public class BillFormatter {

    // Format the bill in a readable way
    public static String formatBill(Bill bill, Customer customer, List<Transaction> transactions) {
        StringBuilder billString = new StringBuilder();

        billString.append("\n\n==== Final Bill ====\n");
        billString.append("Bill Serial Number: ").append(bill.getBillId()).append("\n");
        billString.append("Bill Date: ").append(bill.getBillDate()).append("\n");
        billString.append("Customer Name: ").append(customer.getName()).append("\n");
        billString.append("Phone: ").append(customer.getPhoneNumber()).append("\n");
        billString.append("Email: ").append(customer.getEmail()).append("\n\n");

        billString.append("Items Purchased:\n");

        for (Transaction transaction : transactions) {
            if (transaction.getItem() != null) {  // Ensure the item is not null
                billString.append(" - ").append(transaction.getItem().getItemName())
                        .append(" (Quantity: ").append(transaction.getQuantity())
                        .append(", Price: ").append(transaction.getTotalPrice()).append(")\n");
            } else {
                billString.append(" - Unknown Item (Quantity: ").append(transaction.getQuantity())
                        .append(", Price: ").append(transaction.getTotalPrice()).append(")\n");
            }
        }

        billString.append("\nTotal Price: ").append(bill.getTotalPrice()).append("\n");
        billString.append("Discount: ").append(bill.getDiscountAmount()).append("\n");
        billString.append("Final Price (after discount and tax): ").append(bill.getFinalPrice()).append("\n");
        billString.append("Cash Tendered: ").append(bill.getCashTendered()).append("\n");
        billString.append("Change: ").append(bill.getChangeAmount()).append("\n");

        return billString.toString();
    }
}
