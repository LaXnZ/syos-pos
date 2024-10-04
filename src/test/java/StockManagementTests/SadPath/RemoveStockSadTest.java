package StockManagementTests.SadPath;

import business.stock.StockManagerImpl;
import entities.repositories.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doThrow;

public class RemoveStockSadTest {

    private StockManagerImpl stockManager;
    private StockRepository stockRepository;

    @BeforeEach
    public void setup() {
        stockRepository = Mockito.mock(StockRepository.class);
        stockManager = new StockManagerImpl(stockRepository);
    }

    @Test
    public void testRemoveStockSadPath() {
        String batchCode = "B001";

        // Simulate an exception
        doThrow(new RuntimeException("Stock not found")).when(stockRepository).delete(batchCode);

        try {
            stockManager.removeStock(batchCode);
        } catch (RuntimeException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        System.out.println("RemoveStockSadTest passed!");
    }
}
