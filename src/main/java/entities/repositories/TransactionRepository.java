package entities.repositories;

import entities.models.Transaction;

import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);  // Save a new transaction to the database
    List<Transaction> findByBillId(int billId);  // Retrieve all transactions by bill ID
}
