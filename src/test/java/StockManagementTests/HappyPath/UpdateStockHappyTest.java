package StockManagementTests.HappyPath;

import business.stock.StockManagerImpl;
import entities.models.Stock;
import entities.repositories.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

public class UpdateStockHappyTest {

    private StockManagerImpl stockManager;
    private StockRepository stockRepository;

    @BeforeEach
    public void setup() {
        stockRepository = Mockito.mock(StockRepository.class);
        stockManager = new StockManagerImpl(stockRepository);
    }

    @Test
    public void testUpdateStockHappyPath() {
        // Create mock stock
        Stock stock = new Stock("B001", "I001", 50, LocalDate.now().minusDays(10), LocalDate.now().plusDays(30), 20, 100, "Shelf1");

        // Execute the method
        stockManager.updateStock(stock);

        // Verify that the repository update was called
        verify(stockRepository).update(stock);

        System.out.println("UpdateStockHappyTest passed!");
    }
}
