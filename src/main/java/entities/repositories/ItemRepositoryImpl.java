package entities.repositories;

import entities.models.Item;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class ItemRepositoryImpl implements ItemRepository {

    private final Connection connection;

    public ItemRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Item item) {
        // First check if the item already exists by item_code
        if (findByCode(item.getItemCode()) == null) {
            String sql = "INSERT INTO item (item_code, item_name, item_price) VALUES (?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, item.getItemCode());
                statement.setString(2, item.getItemName());
                statement.setBigDecimal(3, item.getItemPrice());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new item was inserted successfully!");
                }
            } catch (SQLException e) {
                System.out.println("Error saving the item: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Item with code " + item.getItemCode() + " already exists. Skipping insertion.");
        }
    }

    @Override
    public Item findById(int itemId) {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        Item item = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, itemId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                item = new Item();
                item.setItemId(resultSet.getInt("item_id"));
                item.setItemCode(resultSet.getString("item_code"));
                item.setItemName(resultSet.getString("item_name"));
                item.setItemPrice(resultSet.getBigDecimal("item_price"));
            }
        } catch (SQLException e) {
            System.out.println("Error finding the item: " + e.getMessage());
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public Item findByCode(String itemCode) {
        String sql = "SELECT * FROM item WHERE item_code = ?";
        Item item = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, itemCode);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                item = new Item();
                item.setItemId(resultSet.getInt("item_id"));
                item.setItemCode(resultSet.getString("item_code"));
                item.setItemName(resultSet.getString("item_name"));
                item.setItemPrice(resultSet.getBigDecimal("item_price"));
            }
        } catch (SQLException e) {
            System.out.println("Error finding the item by code: " + e.getMessage());
            e.printStackTrace();
        }

        return item;
    }
}
