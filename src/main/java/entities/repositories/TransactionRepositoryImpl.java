package entities.repositories;

import entities.models.Transaction;
import entities.models.Bill;
import entities.models.Item;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository {

    private final Connection connection;

    public TransactionRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transaction (bill_id, item_id, quantity, total_price, transaction_date, transaction_type) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, transaction.getBill().getBillId()); // Set bill_id
            statement.setInt(2, transaction.getItem().getItemId()); // Set item_id
            statement.setInt(3, transaction.getQuantity()); // Set quantity
            statement.setBigDecimal(4, transaction.getTotalPrice()); // Set total_price
            statement.setDate(5, java.sql.Date.valueOf(transaction.getTransactionDate())); // Set transaction_date
            statement.setString(6, transaction.getTransactionType()); // Set transaction_type

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new transaction was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error saving the transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public List<Transaction> findByBillId(int billId) {
        String sql = "SELECT * FROM transaction WHERE bill_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getInt("transaction_id"));
                transaction.setQuantity(resultSet.getInt("quantity"));
                transaction.setTotalPrice(resultSet.getBigDecimal("total_price"));
                transaction.setTransactionDate(resultSet.getDate("transaction_date").toLocalDate());
                transaction.setTransactionType(resultSet.getString("transaction_type"));


                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving transactions by Bill ID: " + e.getMessage());
            e.printStackTrace();
        }

        return transactions;
    }
}
