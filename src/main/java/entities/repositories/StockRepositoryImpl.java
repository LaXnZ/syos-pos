package entities.repositories;

import entities.models.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockRepositoryImpl implements StockRepository {

    private final Connection connection;

    public StockRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Stock stock) {
        String sql = "INSERT INTO Stock (batch_code, item_code, quantity_in_stock, date_of_purchase, expiry_date, reshelf_quantity, shelf_capacity, stock_location) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, stock.getBatchCode());
            statement.setString(2, stock.getItemCode());
            statement.setInt(3, stock.getQuantityInStock());
            statement.setDate(4, java.sql.Date.valueOf(stock.getDateOfPurchase()));
            statement.setDate(5, java.sql.Date.valueOf(stock.getExpiryDate()));
            statement.setInt(6, stock.getReshelfQuantity());
            statement.setInt(7, stock.getShelfCapacity());
            statement.setString(8, stock.getStockLocation());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Stock record inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error saving the stock record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Stock findByBatchCode(String batchCode) {
        String sql = "SELECT * FROM Stock WHERE batch_code = ?";
        Stock stock = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, batchCode);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                stock = new Stock(
                        resultSet.getString("batch_code"),
                        resultSet.getString("item_code"),
                        resultSet.getInt("quantity_in_stock"),
                        resultSet.getDate("date_of_purchase").toLocalDate(),
                        resultSet.getDate("expiry_date").toLocalDate(),
                        resultSet.getInt("reshelf_quantity"),
                        resultSet.getInt("shelf_capacity"),
                        resultSet.getString("stock_location")
                );
                stock.setStockId(resultSet.getInt("stock_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error finding stock by batch code: " + e.getMessage());
            e.printStackTrace();
        }

        return stock;
    }

    @Override
    public void update(Stock stock) {
        String sql = "UPDATE Stock SET quantity_in_stock = ?, date_of_purchase = ?, expiry_date = ?, reshelf_quantity = ?, shelf_capacity = ?, stock_location = ? WHERE batch_code = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, stock.getQuantityInStock());
            statement.setDate(2, java.sql.Date.valueOf(stock.getDateOfPurchase()));
            statement.setDate(3, java.sql.Date.valueOf(stock.getExpiryDate()));
            statement.setInt(4, stock.getReshelfQuantity());
            statement.setInt(5, stock.getShelfCapacity());
            statement.setString(6, stock.getStockLocation());
            statement.setString(7, stock.getBatchCode());

            statement.executeUpdate();
            System.out.println("Stock updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating stock: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String batchCode) {
        String sql = "DELETE FROM Stock WHERE batch_code = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, batchCode);
            statement.executeUpdate();
            System.out.println("Stock with batch code " + batchCode + " was deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting stock: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Stock> findAll() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM Stock";

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
                stock.setStockId(resultSet.getInt("stock_id"));
                stocks.add(stock);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving stocks: " + e.getMessage());
            e.printStackTrace();
        }

        return stocks;
    }
}
