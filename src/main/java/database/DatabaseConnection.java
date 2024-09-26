package database;

import utils.LoggerUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import utils.ConfigLoader;
import org.slf4j.Logger;

public class DatabaseConnection {
    private static final Logger logger = LoggerUtility.getLogger(DatabaseConnection.class);

    // Load database configuration from properties
    private static final String URL = ConfigLoader.getProperty("db.url");
    private static final String USERNAME = ConfigLoader.getProperty("db.username");
    private static final String PASSWORD = ConfigLoader.getProperty("db.password");

    private Connection connection;

    // Method to establish a connection
    public Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                LoggerUtility.logInfo(logger, "Database connection established");
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
