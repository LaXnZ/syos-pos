package business.stock;

import entities.models.Stock;
import entities.repositories.StockRepository;

import java.util.List;

public class StockManagerImpl implements StockManager {

    private final StockRepository stockRepository;

    public StockManagerImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void addStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public Stock findByBatchCode(String batchCode) {
        return stockRepository.findByBatchCode(batchCode);
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();  // Make sure to implement findAll in the repository
    }

    @Override
    public void updateStock(Stock stock) {
        stockRepository.update(stock);
    }

    @Override
    public void removeStock(String batchCode) {
        stockRepository.delete(batchCode);  // Pass the batch code to delete
    }

    @Override
    public List<Stock> reshelveStock() {
        List<Stock> allStock = stockRepository.findAll();

        // Sort stock based on FIFO and expiry date
        allStock.sort((s1, s2) -> {
            if (s1.getExpiryDate() != null && s2.getExpiryDate() != null) {
                return s1.getExpiryDate().compareTo(s2.getExpiryDate());
            } else {
                return s1.getDateOfPurchase().compareTo(s2.getDateOfPurchase());
            }
        });

        return allStock;
    }
}
