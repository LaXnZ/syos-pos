package entities.models;

import java.time.LocalDate;

public class StoreInventory {

    private int stockId;  // Unique ID for the stock entry
    private String itemCode;  // Link to Item table (foreign key)
    private String batchCode;  // Unique code for each batch of the same item
    private int quantityInStock;  // Current quantity available in stock
    private LocalDate dateOfPurchase;  // Date when this batch was purchased
    private LocalDate expiryDate;  // Expiry date of the batch, if applicable
    private LocalDate dateAddedToShelf;  // Date when the batch was moved to the shelf for sale

    // Constructors
    public StoreInventory() {}

    public StoreInventory(String itemCode, String batchCode, int quantityInStock, LocalDate dateOfPurchase, LocalDate expiryDate, LocalDate dateAddedToShelf) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.quantityInStock = quantityInStock;
        this.dateOfPurchase = dateOfPurchase;
        this.expiryDate = expiryDate;
        this.dateAddedToShelf = dateAddedToShelf;
    }

    // Getters and Setters
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

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
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

    public LocalDate getDateAddedToShelf() {
        return dateAddedToShelf;
    }

    public void setDateAddedToShelf(LocalDate dateAddedToShelf) {
        this.dateAddedToShelf = dateAddedToShelf;
    }

    @Override
    public String toString() {
        return "StoreInventory{" +
                "stockId=" + stockId +
                ", itemCode='" + itemCode + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", quantityInStock=" + quantityInStock +
                ", dateOfPurchase=" + dateOfPurchase +
                ", expiryDate=" + expiryDate +
                ", dateAddedToShelf=" + dateAddedToShelf +
                '}';
    }
}
