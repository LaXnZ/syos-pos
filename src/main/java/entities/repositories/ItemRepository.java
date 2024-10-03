package entities.repositories;

import entities.models.Item;
import java.util.List;

public interface ItemRepository {

    // Method to save an item
    void save(Item item);

    // Method to find an item by its code
    Item findByCode(String itemCode);

    // Method to update an existing item
    void update(Item item);

    // Method to delete an item by its code
    void delete(String itemCode);

    // Method to retrieve all items
    List<Item> findAll();

    Item findById(int itemId);
}
