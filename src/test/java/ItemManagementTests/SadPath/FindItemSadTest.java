package ItemManagementTests.SadPath;

import business.item.ItemManager;
import business.item.ItemManagerImpl;
import entities.models.Item;
import entities.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.logging.Logger;

public class FindItemSadTest {

    private ItemManager itemManager;
    private ItemRepository mockItemRepository;
    private static final Logger LOGGER = Logger.getLogger(FindItemSadTest.class.getName());

    @BeforeEach
    public void setUp() {

        mockItemRepository = Mockito.mock(ItemRepository.class);

        itemManager = new ItemManagerImpl(mockItemRepository);
    }

    @Test
    public void testFindItemByCode_ItemNotFound() {

        String nonExistentItemCode = "INVALID123";
        LOGGER.info("Starting test for non-existent item code: " + nonExistentItemCode);


        when(mockItemRepository.findByCode(nonExistentItemCode)).thenReturn(null);


        Item result = itemManager.findByCode(nonExistentItemCode);


        assertNull(result, "Expected null when item is not found.");
        LOGGER.info("Item with code " + nonExistentItemCode + " not found as expected.");


        verify(mockItemRepository, times(1)).findByCode(nonExistentItemCode);
    }

    @Test
    public void testFindItemByCode_EmptyCode() {

        String emptyItemCode = "";
        LOGGER.warning("Starting test for empty item code.");


        Item result = itemManager.findByCode(emptyItemCode);


        assertNull(result, "Expected null for an empty item code.");
        LOGGER.info("Tested empty item code, result was null as expected.");


        verify(mockItemRepository, times(0)).findByCode(emptyItemCode);
    }

    @Test
    public void testFindItemByCode_NullCode() {

        String nullItemCode = null;
        LOGGER.warning("Starting test for null item code.");


        Item result = itemManager.findByCode(nullItemCode);


        assertNull(result, "Expected null for a null item code.");
        LOGGER.info("Tested null item code, result was null as expected.");


        verify(mockItemRepository, times(0)).findByCode(nullItemCode);
    }
}
