package entities.repositories;

import entities.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final Connection connection;  // Connection passed through constructor

    public CustomerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customer (name, phone_number, email, loyalty_points, total_spent, last_purchase_date) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING customer_id";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhoneNumber());
            statement.setString(3, customer.getEmail());
            statement.setInt(4, customer.getLoyaltyPoints());
            statement.setBigDecimal(5, customer.getTotalSpent());
            statement.setDate(6, java.sql.Date.valueOf(customer.getLastPurchaseDate()));

            ResultSet resultSet = statement.executeQuery();  // Execute the query and retrieve the customer_id
            if (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));  // Set the generated customer_id
                System.out.println("A new customer was inserted successfully with ID: " + customer.getCustomerId());
            }
        } catch (SQLException e) {
            System.out.println("Error saving the customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Customer findById(int customerId) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        Customer customer = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customer_id"));
                customer.setName(resultSet.getString("name"));
                customer.setPhoneNumber(resultSet.getString("phone_number"));
                customer.setEmail(resultSet.getString("email"));
                customer.setLoyaltyPoints(resultSet.getInt("loyalty_points"));
                customer.setTotalSpent(resultSet.getBigDecimal("total_spent"));
                customer.setLastPurchaseDate(resultSet.getDate("last_purchase_date").toLocalDate());
            }
        } catch (SQLException e) {
            System.out.println("Error finding the customer: " + e.getMessage());
            e.printStackTrace();
        }

        return customer;
    }
}
