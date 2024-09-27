package model;

public class Item {
    private int itemId;
    private String itemCode;
    private String itemName;
    private double itemPrice;

    // Constructor, Getters, and Setters
    public Item(int itemId, String itemCode, String itemName, double itemPrice) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
