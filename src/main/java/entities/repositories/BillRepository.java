package entities.repositories;

import entities.models.Bill;

public interface BillRepository {
    void save(Bill bill);
    Bill findById(int billId);
}