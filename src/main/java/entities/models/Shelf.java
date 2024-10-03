package entities.models;

import java.time.LocalDate;

public class Shelf {
    private int shelfId;
    private String itemCode;
    private int quantity;
    private LocalDate movedDate;
    private LocalDate expiryDate;
    private String batchCode;  // Batch code to track batches for reshelving

    // Constructor for creating new Shelf entries
    public Shelf(String itemCode, int quantity, LocalDate movedDate, LocalDate expiryDate, String batchCode) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.movedDate = movedDate;
        this.expiryDate = expiryDate;
        this.batchCode = batchCode;
    }

    // Default constructor for database retrieval
    public Shelf() {
    }

    // Getters and setters
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

    public LocalDate getMovedDate() {
        return movedDate;
    }

    public void setMovedDate(LocalDate movedDate) {
        this.movedDate = movedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "shelfId=" + shelfId +
                ", itemCode='" + itemCode + '\'' +
                ", quantity=" + quantity +
                ", movedDate=" + movedDate +
                ", expiryDate=" + expiryDate +
                ", batchCode='" + batchCode + '\'' +
                '}';
    }
}
