package StockManagementTests.HappyPath;

import business.stock.StockManagerImpl;
import entities.models.Stock;
import entities.repositories.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReshelveStockHappyTest {

    private StockManagerImpl stockManager;
    private StockRepository stockRepository;

    @BeforeEach
    public void setup() {
        stockRepository = Mockito.mock(StockRepository.class);
        stockManager = new StockManagerImpl(stockRepository);
    }

    @Test
    public void testReshelveStockHappyPath() {
        // Prepare mock data
        Stock stock1 = new Stock("B001", "I001", 50, LocalDate.now().minusDays(10), LocalDate.now().plusDays(5), 10, 100, "Shelf1");
        Stock stock2 = new Stock("B002", "I002", 20, LocalDate.now().minusDays(5), LocalDate.now().plusDays(10), 30, 50, "Shelf2");
        List<Stock> mockStocks = Arrays.asList(stock1, stock2);

        // Mock repository behavior
        Mockito.when(stockRepository.findAll()).thenReturn(mockStocks);

        // Execute the method
        List<Stock> result = stockManager.reshelveStock();

        // Verify the results
        assertEquals(2, result.size());
        assertEquals(stock1.getBatchCode(), result.get(0).getBatchCode());

        System.out.println("ReshelveStockHappyTest passed!");
    }
}
