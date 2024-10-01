package business.stock;

import entities.models.Stock;
import java.util.List;

public interface StockManager {
    void addStock(Stock stock);
    Stock findByBatchCode(String batchCode);
    List<Stock> findAll();  // Make sure this is added
    void updateStock(Stock stock);
    void removeStock(String batchCode);
    List<Stock> reshelveStock(); // Reshelve stock logic
}
