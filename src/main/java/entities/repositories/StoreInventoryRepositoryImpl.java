package entities.repositories;

import entities.models.StoreInventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreInventoryRepositoryImpl implements StoreInventoryRepository {

    private final Connection connection;

    public StoreInventoryRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<StoreInventory> findByItemCodeOrderedByExpiry(String itemCode) {
        String sql = "SELECT * FROM StoreInventory WHERE item_code = ? ORDER BY expiry_date ASC";
        List<StoreInventory> inventories = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, itemCode);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Use the correct constructor
                StoreInventory inventory = new StoreInventory(
                        resultSet.getString("item_code"),
                        resultSet.getInt("quantity_in_stock"),
                        resultSet.getDate("date_of_purchase").toLocalDate(),
                        resultSet.getDate("expiry_date").toLocalDate()
                );
                inventory.setStockId(resultSet.getInt("stock_id"));
                inventories.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventories;
    }


    @Override
    public void update(StoreInventory storeInventory) {
        String sql = "UPDATE StoreInventory SET quantity_in_stock = ?, expiry_date = ? WHERE stock_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, storeInventory.getQuantityInStock());
            statement.setDate(2, java.sql.Date.valueOf(storeInventory.getExpiryDate()));
            statement.setInt(3, storeInventory.getStockId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
