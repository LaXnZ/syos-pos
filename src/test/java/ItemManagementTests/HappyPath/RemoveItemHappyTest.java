package ItemManagementTests.HappyPath;

import business.item.ItemManagerImpl;
import entities.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class RemoveItemHappyTest {

    private ItemRepository itemRepository;
    private ItemManagerImpl itemManager;

    @BeforeEach
    public void setup() {
        itemRepository = mock(ItemRepository.class);
        itemManager = new ItemManagerImpl(itemRepository);
        System.out.println("Setup: Mocked ItemRepository and initialized ItemManager.");
    }

    @Test
    public void testRemoveItem() {
        // Given
        String itemCode = "IT001";
        System.out.println("Test: Attempting to remove an item with code IT001.");

        // When
        itemManager.removeItem(itemCode);
        System.out.println("Action: Removal of the item executed.");

        // Then
        verify(itemRepository, times(1)).delete(itemCode);
        System.out.println("Verification: Item was successfully removed from the repository.");
    }
}
