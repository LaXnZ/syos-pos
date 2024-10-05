package business.item;

import entities.models.Item;
import java.util.List;

public interface ItemManager {
    void addItem(Item item);
    Item findByCode(String itemCode);
    void updateItem(Item item);
    void removeItem(String itemCode);
    List<Item> getAllItems();
}
