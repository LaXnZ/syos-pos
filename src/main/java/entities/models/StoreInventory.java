package entities.models;


import java.time.LocalDate;

public class StoreInventory {
    private int stockId;
    private String itemCode;
    private int quantityInStock;
    private LocalDate dateOfPurchase;
    private LocalDate expiryDate;


    public StoreInventory(String itemCode, int quantityInStock, LocalDate dateOfPurchase, LocalDate expiryDate) {
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

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
