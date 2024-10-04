package StockManagementTests.HappyPath;

import business.stock.StockManagerImpl;
import entities.repositories.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class RemoveStockHappyTest {

    private StockManagerImpl stockManager;
    private StockRepository stockRepository;

    @BeforeEach
    public void setup() {
        stockRepository = Mockito.mock(StockRepository.class);
        stockManager = new StockManagerImpl(stockRepository);
    }

    @Test
    public void testRemoveStockHappyPath() {
        String batchCode = "B001";

        // Execute the method
        stockManager.removeStock(batchCode);

        // Verify that the repository delete was called
        verify(stockRepository).delete(batchCode);

        System.out.println("RemoveStockHappyTest passed!");
    }
}
