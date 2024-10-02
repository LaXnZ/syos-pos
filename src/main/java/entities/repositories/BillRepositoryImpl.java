package entities.repositories;

import entities.models.Bill;
import entities.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BillRepositoryImpl implements BillRepository {

    private final Connection connection;

    public BillRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Bill bill) {
        String sql;
        if (bill.getBillId() == 0) {
            sql = "INSERT INTO bill (bill_date, total_price, discount_amount, tax_amount, final_price, cash_tendered, change_amount, customer_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING bill_id";
        } else {
            sql = "UPDATE bill SET bill_date = ?, total_price = ?, discount_amount = ?, tax_amount = ?, final_price = ?, cash_tendered = ?, change_amount = ?, customer_id = ? " +
                    "WHERE bill_id = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(bill.getBillDate()));
            statement.setBigDecimal(2, bill.getTotalPrice());
            statement.setBigDecimal(3, bill.getDiscountAmount());
            statement.setBigDecimal(4, bill.getTaxAmount());
            statement.setBigDecimal(5, bill.getFinalPrice());
            statement.setBigDecimal(6, bill.getCashTendered());
            statement.setBigDecimal(7, bill.getChangeAmount());
            statement.setInt(8, bill.getCustomer().getCustomerId());

            if (bill.getBillId() != 0) {
                statement.setInt(9, bill.getBillId());
                statement.executeUpdate();
            } else {
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    bill.setBillId(rs.getInt(1));
                }
            }

            // Update customer details after bill
            updateCustomerDetailsAfterBill(bill.getCustomer(), bill.getTotalPrice());

            // Update customer transaction history
            updateCustomerTransactionHistory(bill.getCustomer(), bill.getTotalPrice());


        } catch (SQLException e) {
            System.out.println("Error saving the bill: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Bill findById(int billId) {
        String sql = "SELECT * FROM bill WHERE bill_id = ?";
        Bill bill = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bill = new Bill();
                bill.setBillId(resultSet.getInt("bill_id"));
                bill.setBillDate(resultSet.getDate("bill_date").toLocalDate());
                bill.setTotalPrice(resultSet.getBigDecimal("total_price"));
                bill.setDiscountAmount(resultSet.getBigDecimal("discount_amount"));
                bill.setTaxAmount(resultSet.getBigDecimal("tax_amount"));
                bill.setFinalPrice(resultSet.getBigDecimal("final_price"));
                bill.setCashTendered(resultSet.getBigDecimal("cash_tendered"));
                bill.setChangeAmount(resultSet.getBigDecimal("change_amount"));
            }
        } catch (SQLException e) {
            System.out.println("Error finding the bill: " + e.getMessage());
            e.printStackTrace();
        }

        return bill;
    }

    private void updateCustomerDetailsAfterBill(Customer customer, BigDecimal totalSpent) {
        String updateSql = "UPDATE customer SET loyalty_points = ?, total_spent = ?, last_purchase_date = ? WHERE customer_id = ?";
        int loyaltyPointsEarned = totalSpent.multiply(BigDecimal.valueOf(0.05)).intValue();  // 5% of the total bill as loyalty points

        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
            int newLoyaltyPoints = customer.getLoyaltyPoints() + loyaltyPointsEarned;
            BigDecimal newTotalSpent = customer.getTotalSpent().add(totalSpent);

            updateStmt.setInt(1, newLoyaltyPoints);
            updateStmt.setBigDecimal(2, newTotalSpent);
            updateStmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            updateStmt.setInt(4, customer.getCustomerId());
            updateStmt.executeUpdate();

            System.out.println("Customer loyalty points and last purchase date updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating customer details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Update or insert into CustomerTransactionHistory
    private void updateCustomerTransactionHistory(Customer customer, BigDecimal totalSpent) {
        String findHistorySql = "SELECT * FROM customertransactionhistory WHERE customer_id = ?";
        String insertHistorySql = "INSERT INTO customertransactionhistory (customer_id, total_purchases, total_spent, avg_spent_per_purchase, purchase_frequency, last_purchase_date, data_as_of) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String updateHistorySql = "UPDATE customertransactionhistory SET total_purchases = ?, total_spent = ?, avg_spent_per_purchase = ?, purchase_frequency = ?, last_purchase_date = ?, data_as_of = ? " +
                "WHERE customer_id = ?";

        try (PreparedStatement findStatement = connection.prepareStatement(findHistorySql)) {
            findStatement.setInt(1, customer.getCustomerId());
            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next()) {
                // Update existing record
                int totalPurchases = resultSet.getInt("total_purchases") + 1;
                BigDecimal totalSpentInHistory = resultSet.getBigDecimal("total_spent").add(totalSpent);
                BigDecimal avgSpent = totalSpentInHistory.divide(BigDecimal.valueOf(totalPurchases), BigDecimal.ROUND_HALF_UP);
                int frequency = resultSet.getInt("purchase_frequency") + 1;

                try (PreparedStatement updateStatement = connection.prepareStatement(updateHistorySql)) {
                    updateStatement.setInt(1, totalPurchases);
                    updateStatement.setBigDecimal(2, totalSpentInHistory);
                    updateStatement.setBigDecimal(3, avgSpent);
                    updateStatement.setInt(4, frequency);
                    updateStatement.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                    updateStatement.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
                    updateStatement.setInt(7, customer.getCustomerId());
                    updateStatement.executeUpdate();

                    System.out.println("Customer transaction history updated successfully!");
                }
            } else {
                // Insert new record
                try (PreparedStatement insertStatement = connection.prepareStatement(insertHistorySql)) {
                    insertStatement.setInt(1, customer.getCustomerId());
                    insertStatement.setInt(2, 1);  // First purchase
                    insertStatement.setBigDecimal(3, totalSpent);
                    insertStatement.setBigDecimal(4, totalSpent);  // Avg spent is the total spent on first purchase
                    insertStatement.setInt(5, 1);  // First purchase
                    insertStatement.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
                    insertStatement.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
                    insertStatement.executeUpdate();

                    System.out.println("Customer transaction history inserted successfully!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating customer transaction history: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Method to apply loyalty points as discount
    public void applyLoyaltyPointsDiscount(Bill bill, Customer customer) {
        int loyaltyPoints = customer.getLoyaltyPoints();
        BigDecimal discount = BigDecimal.valueOf(loyaltyPoints);

        if (bill.getTotalPrice().compareTo(discount) >= 0) {
            bill.setDiscountAmount(bill.getDiscountAmount().add(discount));
            bill.setFinalPrice(bill.getTotalPrice().subtract(discount));
            customer.setLoyaltyPoints(0);

            System.out.println("Loyalty points applied as discount successfully!");
        } else {
            System.out.println("Loyalty points exceed total bill amount, cannot apply.");
        }

        save(bill);  // Update the bill with discount and final price
    }

    // Method to calculate change after cash tendered
    public void finalizeBill(Bill bill, double cashTendered) {
        bill.setCashTendered(BigDecimal.valueOf(cashTendered));
        BigDecimal changeAmount = bill.getCashTendered().subtract(bill.getFinalPrice());
        bill.setChangeAmount(changeAmount);

        save(bill);  // Save the final bill
        System.out.println("Bill finalized. Change: " + changeAmount);
    }
}
