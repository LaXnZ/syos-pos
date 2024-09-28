package entities.models;

import java.math.BigDecimal;

public class Transaction {
    private Item item;
    private BigDecimal quantity;
    private BigDecimal totalPrice;

    public Transaction(Item item, BigDecimal quantity, BigDecimal totalPrice) {
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public Item getItem() {
        return item;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
