package entities.models;

import java.time.LocalDate;

public class Stock {
    private int stockId;
    private String batchCode;
    private String itemCode;
    private int quantityInStock;
    private int reshelfQuantity;
    private int shelfCapacity;
    private int reorderLevel;
    private LocalDate dateOfPurchase;
    private LocalDate expiryDate;
    private String stockLocation;

    public Stock() {
    }

    public Stock(String batchCode, String itemCode, int quantityInStock, LocalDate dateOfPurchase, LocalDate expiryDate,
                 int reshelfQuantity, int shelfCapacity, String stockLocation) {
        this.batchCode = batchCode;
        this.itemCode = itemCode;
        this.quantityInStock = quantityInStock;
        this.dateOfPurchase = dateOfPurchase;
        this.expiryDate = expiryDate;
        this.reshelfQuantity = reshelfQuantity;
        this.shelfCapacity = shelfCapacity;
        this.stockLocation = stockLocation;
    }

    // Getters and setters
    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
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

    public int getReshelfQuantity() {
        return reshelfQuantity;
    }

    public void setReshelfQuantity(int reshelfQuantity) {
        this.reshelfQuantity = reshelfQuantity;
    }

    public int getShelfCapacity() {
        return shelfCapacity;
    }

    public void setShelfCapacity(int shelfCapacity) {
        this.shelfCapacity = shelfCapacity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
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

    public String getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(String stockLocation) {
        this.stockLocation = stockLocation;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", batchCode='" + batchCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", quantityInStock=" + quantityInStock +
                ", reshelfQuantity=" + reshelfQuantity +
                ", shelfCapacity=" + shelfCapacity +
                ", reorderLevel=" + reorderLevel +
                ", dateOfPurchase=" + dateOfPurchase +
                ", expiryDate=" + expiryDate +
                ", stockLocation='" + stockLocation + '\'' +
                '}';
    }
}
