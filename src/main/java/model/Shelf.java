package model;

import java.util.Date;

public class Shelf {
    private int shelfId;
    private String itemCode;
    private int quantity;
    private Date movedDate;
    private Date expiryDate;

    // Constructor, Getters, and Setters
    public Shelf(String itemCode, int quantity, Date movedDate, Date expiryDate) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.movedDate = movedDate;
        this.expiryDate = expiryDate;
    }

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getMovedDate() {
        return movedDate;
    }

    public void setMovedDate(Date movedDate) {
        this.movedDate = movedDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
