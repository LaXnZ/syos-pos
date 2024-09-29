package entities.repositories;

import entities.models.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {

    private final Connection connection;

    public ItemRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Item item) {
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

    @Override
    public void update(Item item) {
        String sql = "UPDATE item SET item_name = ?, item_price = ? WHERE item_code = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getItemName());
            statement.setBigDecimal(2, item.getItemPrice());
            statement.setString(3, item.getItemCode());
            statement.executeUpdate();
            System.out.println("Item updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating item: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String itemCode) {
        String sql = "DELETE FROM item WHERE item_code = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, itemCode);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Item with code " + itemCode + " was deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getInt("item_id"));
                item.setItemCode(resultSet.getString("item_code"));
                item.setItemName(resultSet.getString("item_name"));
                item.setItemPrice(resultSet.getBigDecimal("item_price"));
                items.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving items: " + e.getMessage());
            e.printStackTrace();
        }

        return items;
    }
}
