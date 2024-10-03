package business.stock;

import entities.models.Stock;

import java.util.List;

public interface StockManager {
    void addStock(Stock stock);
    Stock findByBatchCode(String batchCode);
    List<Stock> findAll();
    void updateStock(Stock stock);
    void removeStock(String batchCode);
    List<Stock> reshelveStock(); // Reshelve stock based on expiry dates and available quantity
}
