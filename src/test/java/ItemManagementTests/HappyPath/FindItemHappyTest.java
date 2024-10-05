package ItemManagementTests.HappyPath;

import business.item.ItemManagerImpl;
import entities.models.Item;
import entities.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FindItemHappyTest {

    private ItemRepository itemRepository;
    private ItemManagerImpl itemManager;

    @BeforeEach
    public void setup() {
        itemRepository = mock(ItemRepository.class);
        itemManager = new ItemManagerImpl(itemRepository);
        System.out.println("Setup: Mocked ItemRepository and initialized ItemManager.");
    }

    @Test
    public void testFindItemByCode() {

        String itemCode = "IT001";
        Item mockItem = new Item(itemCode, "Test Item", BigDecimal.valueOf(100.00));
        when(itemRepository.findByCode(itemCode)).thenReturn(mockItem);
        System.out.println("Test: Attempting to find an item with code IT001.");


        Item foundItem = itemManager.findByCode(itemCode);
        System.out.println("Action: Item search executed.");


        assertNotNull(foundItem, "Item should not be null.");
        assertEquals(itemCode, foundItem.getItemCode(), "Item code should match.");
        verify(itemRepository, times(1)).findByCode(itemCode);
        System.out.println("Verification: Item was found successfully and code matches.");
    }
}
