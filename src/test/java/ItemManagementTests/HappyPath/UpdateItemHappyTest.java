package ItemManagementTests.HappyPath;

import business.item.ItemManagerImpl;
import entities.models.Item;
import entities.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class UpdateItemHappyTest {

    private ItemRepository itemRepository;
    private ItemManagerImpl itemManager;

    @BeforeEach
    public void setup() {
        itemRepository = mock(ItemRepository.class);
        itemManager = new ItemManagerImpl(itemRepository);
        System.out.println("Setup: Mocked ItemRepository and initialized ItemManager.");
    }

    @Test
    public void testUpdateItem() {

        Item item = new Item("IT001", "Test Item", BigDecimal.valueOf(100.00));
        System.out.println("Test: Attempting to update item with code IT001.");


        itemManager.updateItem(item);
        System.out.println("Action: Update operation executed.");


        verify(itemRepository, times(1)).update(item);
        System.out.println("Verification: Item was successfully updated in the repository.");
    }
}
