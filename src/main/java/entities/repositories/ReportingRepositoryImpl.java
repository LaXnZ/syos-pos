package entities.repositories;

import entities.models.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportingRepositoryImpl implements ReportingRepository {

    private final Connection connection;

    public ReportingRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Object[]> getTotalSalesReport() {
        List<Object[]> report = new ArrayList<>();
        String sql = """
            SELECT i.item_name, SUM(t.quantity), SUM(t.total_price)
            FROM transaction t
            JOIN item i ON t.item_id = i.item_id
            GROUP BY i.item_name
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getString(1),  // Item name
                        resultSet.getInt(2),     // Quantity sold
                        resultSet.getBigDecimal(3) // Total price
                };
                report.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public List<Stock> getReshelvingReport() {
        List<Stock> report = new ArrayList<>();
        String sql = """
            SELECT *
            FROM stock
            WHERE quantity_in_stock > 0
            AND reshelf_quantity > 0
            ORDER BY expiry_date ASC, date_of_purchase ASC
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public List<Stock> getReorderLevelReport() {
        List<Stock> report = new ArrayList<>();
        String sql = "SELECT * FROM stock WHERE quantity_in_stock < ? ORDER BY item_code ASC";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 50); // Reorder threshold, can be adjusted as needed
            ResultSet resultSet = statement.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public List<Object[]> getBillReport() {
        String sql = "SELECT b.bill_id, c.customer_id, b.total_price " +
                "FROM Bill b " +
                "JOIN Customer c ON b.customer_id = c.customer_id";

        List<Object[]> results = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getInt("bill_id");
                row[1] = resultSet.getInt("customer_id");
                row[2] = resultSet.getBigDecimal("total_price");
                results.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bill report: " + e.getMessage());
            e.printStackTrace();
        }

        return results;
    }

    @Override
    public List<Stock> getStockReport() {
        List<Stock> report = new ArrayList<>();
        String sql = """
            SELECT *
            FROM stock
            WHERE quantity_in_stock > 0
            ORDER BY expiry_date ASC, date_of_purchase ASC
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }


}
