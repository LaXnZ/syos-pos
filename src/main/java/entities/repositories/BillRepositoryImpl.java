package entities.repositories;

import entities.models.Bill;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillRepositoryImpl implements BillRepository {

    private final Connection connection;

    public BillRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Bill bill) {
        // If the bill is new (billId is 0), insert it. Otherwise, update the existing bill.
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
                // For updating an existing bill, set the billId in the query.
                statement.setInt(9, bill.getBillId());
                statement.executeUpdate();
            } else {
                // For inserting a new bill, get the generated bill ID.
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    bill.setBillId(rs.getInt(1));
                }
            }

            System.out.println("Bill inserted/updated successfully!");
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
                // You might also want to load the associated customer here
            }
        } catch (SQLException e) {
            System.out.println("Error finding the bill: " + e.getMessage());
            e.printStackTrace();
        }

        return bill;
    }
}
