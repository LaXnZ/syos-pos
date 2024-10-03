package entities.repositories;

import entities.models.StoreInventory;
import java.util.List;

public interface StoreInventoryRepository {
    List<StoreInventory> findByItemCodeOrderedByExpiry(String itemCode);
    void update(StoreInventory storeInventory);
}
