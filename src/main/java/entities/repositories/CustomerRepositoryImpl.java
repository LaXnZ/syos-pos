package entities.repositories;

import entities.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final Connection connection;

    public CustomerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    // Save method with proper phone number validation
    @Override
    public void save(Customer customer) {
        if (!isValidPhoneNumber(customer.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }

        String sql = "INSERT INTO customer (name, phone_number, email, loyalty_points, total_spent, last_purchase_date) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING customer_id";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhoneNumber());
            statement.setString(3, customer.getEmail());
            statement.setInt(4, customer.getLoyaltyPoints());
            statement.setBigDecimal(5, customer.getTotalSpent());
            statement.setDate(6, java.sql.Date.valueOf(customer.getLastPurchaseDate()));

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
            } else {
                throw new SQLException("Failed to insert customer, no ID obtained.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving customer: " + e.getMessage());
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
                customer = mapCustomer(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error finding customer: " + e.getMessage());
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public Customer findByPhoneNumber(String phoneNumber) {  // Implement find by phone number
        String sql = "SELECT * FROM customer WHERE phone_number = ?";
        Customer customer = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, phoneNumber);

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
            System.out.println("Error finding customer by phone number: " + e.getMessage());
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public void update(Customer customer) {
        if (!isValidPhoneNumber(customer.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }

        String sql = "UPDATE customer SET name = ?, phone_number = ?, email = ?, loyalty_points = ?, total_spent = ?, last_purchase_date = ? WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhoneNumber());
            statement.setString(3, customer.getEmail());
            statement.setInt(4, customer.getLoyaltyPoints());
            statement.setBigDecimal(5, customer.getTotalSpent());
            statement.setDate(6, java.sql.Date.valueOf(customer.getLastPurchaseDate()));
            statement.setInt(7, customer.getCustomerId());
            statement.executeUpdate();
            System.out.println("Customer updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int customerId) {
        // Step 1: Delete dependent records in the Bill and Transaction tables (if they exist)
        deleteRelatedBills(customerId);
        deleteRelatedTransactions(customerId);

        // Step 2: Proceed to delete the customer after dependent records are removed
        String sql = "DELETE FROM customer WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
            System.out.println("Customer with ID " + customerId + " deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to delete all bills related to the customer
    private void deleteRelatedBills(int customerId) {
        String sql = "DELETE FROM bill WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
            System.out.println("All bills for customer ID " + customerId + " deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting bills: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to delete all transactions related to the customer (via bill)
    private void deleteRelatedTransactions(int customerId) {
        String sql = "DELETE FROM transaction WHERE bill_id IN (SELECT bill_id FROM bill WHERE customer_id = ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
            System.out.println("All transactions for customer ID " + customerId + " deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting transactions: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                customers.add(mapCustomer(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all customers: " + e.getMessage());
            e.printStackTrace();
        }

        return customers;
    }

    // Private helper to validate phone number
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");  // Validates if the phone number has exactly 10 digits
    }

    // Private helper to map result set to Customer object
    private Customer mapCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getInt("customer_id"));
        customer.setName(resultSet.getString("name"));
        customer.setPhoneNumber(resultSet.getString("phone_number"));
        customer.setEmail(resultSet.getString("email"));
        customer.setLoyaltyPoints(resultSet.getInt("loyalty_points"));
        customer.setTotalSpent(resultSet.getBigDecimal("total_spent"));
        customer.setLastPurchaseDate(resultSet.getDate("last_purchase_date").toLocalDate());
        return customer;
    }
}
