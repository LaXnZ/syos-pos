package ItemManagementTests.SadPath;

import business.item.ItemManagerImpl;
import entities.models.Item;
import entities.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class AddItemSadTest {

    private ItemRepository itemRepository;
    private ItemManagerImpl itemManager;

    @BeforeEach
    public void setup() {
        itemRepository = mock(ItemRepository.class);
        itemManager = new ItemManagerImpl(itemRepository);
        System.out.println("Setup: Mocked ItemRepository and initialized ItemManager.");
    }

    @Test
    public void testAddItemFailsForExistingCode() {

        Item existingItem = new Item("IT001", "Existing Item", BigDecimal.valueOf(200.00));
        when(itemRepository.findByCode("IT001")).thenReturn(existingItem);
        System.out.println("Test: Attempting to add an item with an existing code IT001.");


        Item newItem = new Item("IT001", "Test Item", BigDecimal.valueOf(100.00));
        itemManager.addItem(newItem);
        System.out.println("Action: Item addition failed due to existing code.");


        verify(itemRepository, never()).save(newItem);
        System.out.println("Verification: Item was not saved as expected because the code already exists.");
    }
}
