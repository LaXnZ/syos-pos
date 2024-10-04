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
        // Create a mock of the ItemRepository
        mockItemRepository = Mockito.mock(ItemRepository.class);
        // Inject the mock into the ItemManager
        itemManager = new ItemManagerImpl(mockItemRepository);
    }

    @Test
    public void testFindItemByCode_ItemNotFound() {
        // Given an item code that does not exist
        String nonExistentItemCode = "INVALID123";
        LOGGER.info("Starting test for non-existent item code: " + nonExistentItemCode);

        // Mock the repository to return null when searching for the item
        when(mockItemRepository.findByCode(nonExistentItemCode)).thenReturn(null);

        // When calling the findByCode method
        Item result = itemManager.findByCode(nonExistentItemCode);

        // Then it should return null or handle the case gracefully
        assertNull(result, "Expected null when item is not found.");
        LOGGER.info("Item with code " + nonExistentItemCode + " not found as expected.");

        // Verify that the repository method was called once
        verify(mockItemRepository, times(1)).findByCode(nonExistentItemCode);
    }

    @Test
    public void testFindItemByCode_EmptyCode() {
        // Given an empty item code
        String emptyItemCode = "";
        LOGGER.warning("Starting test for empty item code.");

        // When calling the findByCode method
        Item result = itemManager.findByCode(emptyItemCode);

        // Then it should return null or handle the case gracefully (depends on your logic)
        assertNull(result, "Expected null for an empty item code.");
        LOGGER.info("Tested empty item code, result was null as expected.");

        // Verify that the repository method was not called since the input was invalid
        verify(mockItemRepository, times(0)).findByCode(emptyItemCode);
    }

    @Test
    public void testFindItemByCode_NullCode() {
        // Given a null item code
        String nullItemCode = null;
        LOGGER.warning("Starting test for null item code.");

        // When calling the findByCode method
        Item result = itemManager.findByCode(nullItemCode);

        // Then it should return null or handle the case gracefully (depends on your logic)
        assertNull(result, "Expected null for a null item code.");
        LOGGER.info("Tested null item code, result was null as expected.");

        // Verify that the repository method was not called since the input was invalid
        verify(mockItemRepository, times(0)).findByCode(nullItemCode);
    }
}
