package database;

import config.DatabaseConfig;
import utils.LoggerUtility;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final Logger logger = LoggerUtility.getLogger(DatabaseConnection.class);
    private static DatabaseConnection instance;  // Singleton instance
    private Connection connection;

    // Private constructor to prevent external instantiation
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(DatabaseConfig.getUrl(),
                    DatabaseConfig.getUsername(),
                    DatabaseConfig.getPassword());
            LoggerUtility.logSuccess(logger, "Database connection established");
        } catch (SQLException e) {
            LoggerUtility.logError(logger, "Failed to connect to the database", e);
        }
    }

    // Method to get the Singleton instance
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Method to get the connection object
    public Connection getConnection() {
        return connection;
    }

    // Method to disconnect from the database
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LoggerUtility.logInfo(logger, "Disconnected from the PostgreSQL server.");
            }
        } catch (SQLException e) {
            LoggerUtility.logError(logger, "Error while disconnecting from the database", e);
        }
    }
}
