package model;

import java.util.Date;

public class Transaction {
    private int transactionId;
    private int billId;
    private int itemId;
    private int quantity;
    private double totalPrice;
    private Date transactionDate;
    private String transactionType;

    // Constructor, Getters, and Setters
    public Transaction(int transactionId, int billId, int itemId, int quantity, double totalPrice, String transactionType) {
        this.transactionId = transactionId;
        this.billId = billId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.transactionType = transactionType;
        this.transactionDate = new Date();  // Default to current date
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
