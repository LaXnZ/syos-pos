package database;

import org.slf4j.Logger;
import utils.LoggerUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import utils.ConfigLoader;

public class DatabaseInitializer {

    // Database configuration properties
    private static final String URL = ConfigLoader.getProperty("db.url");
    private static final String USERNAME = ConfigLoader.getProperty("db.username");
    private static final String PASSWORD = ConfigLoader.getProperty("db.password");

    private static final Logger logger = LoggerUtility.getLogger(DatabaseInitializer.class);

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
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
            LoggerUtility.logInfo(logger, "Database initialized successfully.");

        } catch (SQLException e) {
            // Log error if initialization fails
            LoggerUtility.logError(logger, "Database initialization failed", e);
        }
    }
}
