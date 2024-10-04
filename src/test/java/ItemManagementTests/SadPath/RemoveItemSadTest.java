package ItemManagementTests.SadPath;

import business.item.ItemManagerImpl;
import entities.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class RemoveItemSadTest {

    private ItemRepository itemRepository;
    private ItemManagerImpl itemManager;
    private static final Logger LOGGER = Logger.getLogger(RemoveItemSadTest.class.getName());

    @BeforeEach
    public void setup() {
        itemRepository = mock(ItemRepository.class);
        itemManager = new ItemManagerImpl(itemRepository);
    }

    @Test
    public void testRemoveItemFailsForNonExistingCode() {
        // Given
        String itemCode = "IT002";
        LOGGER.info("Attempting to remove non-existent item with code: " + itemCode);
        when(itemRepository.findByCode(itemCode)).thenReturn(null);

        // When
        itemManager.removeItem(itemCode);

        // Then
        verify(itemRepository, never()).delete(itemCode);  // The item should not be deleted
        LOGGER.info("Verified that no deletion occurred for non-existent item code: " + itemCode);
    }
}
