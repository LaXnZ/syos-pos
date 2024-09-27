package model;

import java.util.Date;

public class StoreInventory {
    private int stockId;
    private String itemCode;
    private int quantityInStock;
    private Date dateOfPurchase;
    private Date expiryDate;

    // Constructor, Getters, and Setters
    public StoreInventory(String itemCode, int quantityInStock, Date dateOfPurchase, Date expiryDate) {
        this.itemCode = itemCode;
        this.quantityInStock = quantityInStock;
        this.dateOfPurchase = dateOfPurchase;
        this.expiryDate = expiryDate;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
