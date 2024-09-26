package database;

import config.DatabaseConfig;
import org.slf4j.Logger;
import utils.LoggerUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final Logger logger = LoggerUtility.getLogger(DatabaseInitializer.class);

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.getUrl(),
                DatabaseConfig.getUsername(),
                DatabaseConfig.getPassword());
             Statement stmt = conn.createStatement()) {

            // SQL query to create the Customer table if it does not already exist
            String sql = "CREATE TABLE IF NOT EXISTS Customer (" +
                    "customer_id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "phone_number VARCHAR(10)," +
                    "email VARCHAR(255)," +
                    "loyalty_points INT DEFAULT 0," +
                    "total_spent DECIMAL(10, 2) DEFAULT 0.0," +
                    "last_purchase_date DATE" +
                    ")";
            stmt.executeUpdate(sql);

            // Log successful initialization
            LoggerUtility.logSuccess(logger, "Database initialized successfully.");

        } catch (SQLException e) {
            // Log error if initialization fails
            LoggerUtility.logError(logger, "Database initialization failed", e);
        }
    }
}
