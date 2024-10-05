package entities.repositories;

import entities.models.Transaction;

import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<Transaction> findByBillId(int billId);
}
