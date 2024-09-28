package entities.models;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private final Customer customer;
    private final List<Transaction> transactions;
    private double totalPrice;
    private double discountAmount;
    private double taxAmount;
    private double finalPrice;
    private double cashTendered;
    private double changeAmount;

    public Bill(Customer customer) {
        this.customer = customer;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Item item, int quantity, double totalPrice) {
        transactions.add(new Transaction(item, quantity, totalPrice));
        this.totalPrice += totalPrice;
    }

    public void removeTransaction(Item item) {
        transactions.removeIf(transaction -> transaction.getItem().equals(item));
    }

    public void calculateFinalPrice() {
        finalPrice = (totalPrice - discountAmount) + taxAmount;
    }

    // Getters and setters for taxAmount, discountAmount, etc.
}
