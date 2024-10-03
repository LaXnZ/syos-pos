package business.stock;

import entities.models.Stock;
import entities.repositories.StockRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        return stockRepository.findAll();
    }

    @Override
    public void updateStock(Stock stock) {
        stockRepository.update(stock);
    }

    @Override
    public void removeStock(String batchCode) {
        stockRepository.delete(batchCode);
    }

    @Override
    public List<Stock> reshelveStock() {
        // Find all stock and reshelve items that are near expiration or running low
        List<Stock> allStocks = stockRepository.findAll();
        return allStocks.stream()
                .filter(stock -> stock.getExpiryDate().isBefore(LocalDate.now().plusDays(7)) || stock.getReshelfQuantity() < stock.getShelfCapacity())
                .collect(Collectors.toList());
    }
}
