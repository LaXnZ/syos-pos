package ItemManagementTests.HappyPath;

import business.item.ItemManagerImpl;
import entities.models.Item;
import entities.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class AddItemHappyTest {

    private ItemRepository itemRepository;
    private ItemManagerImpl itemManager;

    @BeforeEach
    public void setup() {
        itemRepository = mock(ItemRepository.class);
        itemManager = new ItemManagerImpl(itemRepository);
        System.out.println("Setup: Mocked ItemRepository and initialized ItemManager.");
    }

    @Test
    public void testAddItem() {
        // Given
        Item item = new Item("IT001", "Test Item", BigDecimal.valueOf(100.00));
        System.out.println("Test: Adding item with code IT001 and price 100.00");

        // When
        itemManager.addItem(item);
        System.out.println("Action: Attempted to add the item.");

        // Then
        verify(itemRepository, times(1)).save(item);
        System.out.println("Verification: Item was successfully added and saved to the repository.");
    }
}
