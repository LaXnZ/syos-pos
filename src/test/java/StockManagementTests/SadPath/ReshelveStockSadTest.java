package StockManagementTests.SadPath;

import business.stock.StockManagerImpl;
import entities.repositories.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReshelveStockSadTest {

    private StockManagerImpl stockManager;
    private StockRepository stockRepository;

    @BeforeEach
    public void setup() {
        stockRepository = Mockito.mock(StockRepository.class);
        stockManager = new StockManagerImpl(stockRepository);
    }

    @Test
    public void testReshelveStockSadPath_NoStockAvailable() {

        Mockito.when(stockRepository.findAll()).thenReturn(Collections.emptyList());


        var result = stockManager.reshelveStock();


        assertTrue(result.isEmpty());

        System.out.println("ReshelveStockSadTest passed!");
    }
}
