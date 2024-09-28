package entities.repositories;

import entities.models.Item;

public interface ItemRepository {
    void save(Item item);
    Item findById(int itemId);
    Item findByCode(String itemCode);
}
