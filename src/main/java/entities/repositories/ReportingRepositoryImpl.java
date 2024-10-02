package entities.repositories;

import entities.models.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportingRepositoryImpl implements ReportingRepository {

    private final Connection connection;

    public ReportingRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Object[]> getTotalSalesReport(LocalDate date) {
        List<Object[]> report = new ArrayList<>();
        String sql = """
            SELECT i.item_name, SUM(t.quantity), SUM(t.total_price)
            FROM transaction t
            JOIN item i ON t.item_id = i.item_id
            WHERE t.transaction_date = ?
            GROUP BY i.item_name
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Object[] row = {
                            resultSet.getString(1),  // Item name
                            resultSet.getInt(2),     // Quantity sold
                            resultSet.getBigDecimal(3) // Total price
                    };
                    report.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public List<Object[]> getMostSoldCategories(LocalDate date) {
        List<Object[]> report = new ArrayList<>();
        String sql = """
            SELECT c.category_name, SUM(t.quantity) 
            FROM transaction t
            JOIN item i ON t.item_id = i.item_id
            JOIN category c ON i.category_id = c.category_id
            WHERE t.transaction_date = ?
            GROUP BY c.category_name
            ORDER BY SUM(t.quantity) DESC
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Object[] row = {
                            resultSet.getString(1),  // Category name
                            resultSet.getInt(2)     // Total sold
                    };
                    report.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public List<Stock> getReshelvingReport(LocalDate date) {
        List<Stock> report = new ArrayList<>();
        String sql = """
            SELECT *
            FROM stock
            WHERE reshelf_quantity > 0
            AND expiry_date >= ?
            ORDER BY expiry_date ASC, date_of_purchase ASC
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Stock stock = new Stock(
                            resultSet.getString("batch_code"),
                            resultSet.getString("item_code"),
                            resultSet.getInt("quantity_in_stock"),
                            resultSet.getDate("date_of_purchase").toLocalDate(),
                            resultSet.getDate("expiry_date").toLocalDate(),
                            resultSet.getInt("reshelf_quantity"),
                            resultSet.getInt("shelf_capacity"),
                            resultSet.getString("stock_location")
                    );
                    report.add(stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public List<Stock> getReorderLevelReport(LocalDate date) {
        List<Stock> report = new ArrayList<>();
        String sql = """
            SELECT * 
            FROM stock
            WHERE quantity_in_stock < ? 
            AND date_of_purchase <= ?
            ORDER BY item_code ASC
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 50);  // Reorder threshold
            statement.setDate(2, java.sql.Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Stock stock = new Stock(
                            resultSet.getString("batch_code"),
                            resultSet.getString("item_code"),
                            resultSet.getInt("quantity_in_stock"),
                            resultSet.getDate("date_of_purchase").toLocalDate(),
                            resultSet.getDate("expiry_date").toLocalDate(),
                            resultSet.getInt("reshelf_quantity"),
                            resultSet.getInt("shelf_capacity"),
                            resultSet.getString("stock_location")
                    );
                    report.add(stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public List<Object[]> getBillReport(LocalDate date) {
        List<Object[]> report = new ArrayList<>();
        String sql = """
            SELECT b.bill_id, b.customer_id, b.final_price
            FROM bill b
            WHERE b.bill_date = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Object[] row = {
                            resultSet.getInt("bill_id"),
                            resultSet.getInt("customer_id"),
                            resultSet.getBigDecimal("final_price")
                    };
                    report.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public List<Stock> getStockReport(LocalDate date) {
        List<Stock> report = new ArrayList<>();
        String sql = """
            SELECT *
            FROM stock
            WHERE quantity_in_stock > 0
            AND date_of_purchase <= ?
            ORDER BY expiry_date ASC, date_of_purchase ASC
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Stock stock = new Stock(
                            resultSet.getString("batch_code"),
                            resultSet.getString("item_code"),
                            resultSet.getInt("quantity_in_stock"),
                            resultSet.getDate("date_of_purchase").toLocalDate(),
                            resultSet.getDate("expiry_date").toLocalDate(),
                            resultSet.getInt("reshelf_quantity"),
                            resultSet.getInt("shelf_capacity"),
                            resultSet.getString("stock_location")
                    );
                    report.add(stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }


}
