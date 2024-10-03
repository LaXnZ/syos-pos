package entities.repositories;

import entities.models.Shelf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShelfRepositoryImpl implements ShelfRepository {

    private final Connection connection;

    public ShelfRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Shelf findByItemCode(String itemCode) {
        String sql = "SELECT * FROM Shelf WHERE item_code = ?";
        Shelf shelf = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, itemCode);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Create Shelf object from database result
                shelf = new Shelf();
                shelf.setShelfId(resultSet.getInt("shelf_id"));
                shelf.setItemCode(resultSet.getString("item_code"));
                shelf.setQuantity(resultSet.getInt("quantity"));
                shelf.setMovedDate(resultSet.getDate("moved_date").toLocalDate());
                shelf.setExpiryDate(resultSet.getDate("expiry_date").toLocalDate());
                shelf.setBatchCode(resultSet.getString("batch_code"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shelf;
    }

    @Override
    public void update(Shelf shelf) {
        String sql = "UPDATE Shelf SET quantity = ?, moved_date = ?, expiry_date = ?, batch_code = ? WHERE shelf_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shelf.getQuantity());
            statement.setDate(2, java.sql.Date.valueOf(shelf.getMovedDate()));
            statement.setDate(3, java.sql.Date.valueOf(shelf.getExpiryDate()));
            statement.setString(4, shelf.getBatchCode());
            statement.setInt(5, shelf.getShelfId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Shelf shelf) {
        String sql = "INSERT INTO Shelf (item_code, quantity, moved_date, expiry_date, batch_code) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, shelf.getItemCode());
            statement.setInt(2, shelf.getQuantity());
            statement.setDate(3, java.sql.Date.valueOf(shelf.getMovedDate()));
            statement.setDate(4, java.sql.Date.valueOf(shelf.getExpiryDate()));
            statement.setString(5, shelf.getBatchCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
