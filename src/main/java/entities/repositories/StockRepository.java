package entities.repositories;

import entities.models.Stock;

import java.util.List;

public interface StockRepository {
    void save(Stock stock);
    Stock findByBatchCode(String batchCode);
    List<Stock> findAll();
    void update(Stock stock);
    void delete(String batchCode);
}
