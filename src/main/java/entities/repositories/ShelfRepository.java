package entities.repositories;

import entities.models.Shelf;

public interface ShelfRepository {
    Shelf findByItemCode(String itemCode);
    void update(Shelf shelf);
    void save(Shelf shelf);
}
