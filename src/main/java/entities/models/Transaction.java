package entities.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private int transactionId;
    private Bill bill;          // Foreign key to Bill
    private Item item;          // Foreign key to Item
    private int quantity;
    private BigDecimal totalPrice;
    private LocalDate transactionDate;
    private String transactionType;
    private int billId;


    public Transaction() {}

    public Transaction(Bill bill, Item item, int quantity, BigDecimal totalPrice, LocalDate transactionDate, String transactionType) {
        this.bill = bill;
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.transactionDate = transactionDate;
        this.transactionType = "cash";

    }


    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public Bill getBill() { return bill; }
    public void setBill(Bill bill) { this.bill = bill; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }

    public void setTransactionType(String transactionType) {
        if (transactionType.equalsIgnoreCase("cash") ||
                transactionType.equalsIgnoreCase("card")) {
            this.transactionType = transactionType;
        } else {
            throw new IllegalArgumentException("Invalid transaction type. It must be 'cash' or 'card'.");
        }
    }

    public String getTransactionType() {
        return transactionType;
    }
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }
}
