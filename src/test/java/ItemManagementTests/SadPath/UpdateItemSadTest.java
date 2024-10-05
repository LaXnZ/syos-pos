package ItemManagementTests.SadPath;

import business.item.ItemManagerImpl;
import entities.models.Item;
import entities.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class UpdateItemSadTest {

    private ItemRepository itemRepository;
    private ItemManagerImpl itemManager;
    private static final Logger LOGGER = Logger.getLogger(UpdateItemSadTest.class.getName());

    @BeforeEach
    public void setup() {
        itemRepository = mock(ItemRepository.class);
        itemManager = new ItemManagerImpl(itemRepository);
    }

    @Test
    public void testUpdateItemFailsForNonExistingCode() {

        Item nonExistingItem = new Item("IT002", "Non-existing Item", BigDecimal.valueOf(100.00));
        LOGGER.info("Attempting to update non-existent item with code: " + nonExistingItem.getItemCode());
        when(itemRepository.findByCode("IT002")).thenReturn(null);


        itemManager.updateItem(nonExistingItem);


        verify(itemRepository, never()).update(nonExistingItem);  // The item should not be updated
        LOGGER.info("Verified that no update occurred for non-existent item code: " + nonExistingItem.getItemCode());
    }
}
