package database;

import config.DatabaseConfig;
import utils.LoggerUtility;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger logger = LoggerUtility.getLogger(DatabaseConnection.class);
    private Connection connection;

    // Method to establish a connection
    public Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DatabaseConfig.getUrl(),
                        DatabaseConfig.getUsername(),
                        DatabaseConfig.getPassword());
                LoggerUtility.logSuccess(logger, "Database connection established");
            }
        } catch (SQLException e) {
            LoggerUtility.logError(logger, "Failed to connect to the database", e);
        }
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
