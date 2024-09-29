package business.item;

import entities.models.Item;
import entities.repositories.ItemRepository;

import java.util.List;

public class ItemManagerImpl implements ItemManager {

    private final ItemRepository itemRepository;

    public ItemManagerImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void addItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Item findByCode(String itemCode) {
        return itemRepository.findByCode(itemCode);
    }

    @Override
    public void updateItem(Item item) {
        itemRepository.update(item);
    }

    @Override
    public void removeItem(String itemCode) {
        itemRepository.delete(itemCode);  // Pass itemCode directly
    }



    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();  // Assuming your repository has a method to find all items
    }
}
