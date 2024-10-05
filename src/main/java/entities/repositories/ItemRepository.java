package entities.repositories;

import entities.models.Item;
import java.util.List;

public interface ItemRepository {


    void save(Item item);
    Item findByCode(String itemCode);
    void update(Item item);
    void delete(String itemCode);
    List<Item> findAll();
    Item findById(int itemId);
}
