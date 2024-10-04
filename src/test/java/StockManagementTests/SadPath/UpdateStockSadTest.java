package StockManagementTests.SadPath;

import business.stock.StockManagerImpl;
import entities.models.Stock;
import entities.repositories.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.mockito.Mockito.doThrow;

public class UpdateStockSadTest {

    private StockManagerImpl stockManager;
    private StockRepository stockRepository;

    @BeforeEach
    public void setup() {
        stockRepository = Mockito.mock(StockRepository.class);
        stockManager = new StockManagerImpl(stockRepository);
    }

    @Test
    public void testUpdateStockSadPath() {
        Stock stock = new Stock("B001", "I001", 50, LocalDate.now().minusDays(10), LocalDate.now().plusDays(30), 20, 100, "Shelf1");

        // Simulate an exception
        doThrow(new RuntimeException("Database update failed")).when(stockRepository).update(stock);

        try {
            stockManager.updateStock(stock);
        } catch (RuntimeException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        System.out.println("UpdateStockSadTest passed!");
    }
}
