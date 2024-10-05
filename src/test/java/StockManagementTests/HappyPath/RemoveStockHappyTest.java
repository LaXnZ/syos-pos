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


        stockManager.removeStock(batchCode);


        verify(stockRepository).delete(batchCode);

        System.out.println("RemoveStockHappyTest passed!");
    }
}
