package database;

import config.DatabaseConfig;
import utils.LoggerUtility;
import org.slf4j.Logger;

import java.io.BufferedReader;  // Import for BufferedReader
import java.io.FileReader;      // Import for FileReader
import java.io.IOException;     // Import for handling IO exceptions
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final Logger logger = LoggerUtility.getLogger(DatabaseInitializer.class);

    // Singleton instance to ensure initialization happens only once
    private static DatabaseInitializer instance = null;
    private boolean isInitialized = false; // Flag to check initialization status

    // Private constructor for Singleton pattern
    private DatabaseInitializer() {
    }

    // Method to get the singleton instance of DatabaseInitializer
    public static synchronized DatabaseInitializer getInstance() {
        if (instance == null) {
            instance = new DatabaseInitializer();
        }
        return instance;
    }

    // Method to check if the database is already initialized (e.g., by checking if a table exists)
    private boolean isDatabaseInitialized(Connection connection) {
        String checkTableSQL = "SELECT 1 FROM information_schema.tables WHERE table_name = 'customer'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(checkTableSQL)) {
            if (rs.next()) {
                LoggerUtility.logInfo(logger, "Database is already initialized.");
                return true; // Table exists, database is already initialized
            }
        } catch (SQLException e) {
            LoggerUtility.logError(logger, "Error checking database initialization", e);
        }
        return false; // Table doesn't exist, database not initialized
    }

    // Method to load and execute SQL script for initializing the database
    public void initializeDatabase() {
        LoggerUtility.logInfo(logger, "Checking database initialization...");

        // Only initialize the database if it hasn't been initialized yet
        if (!isInitialized) {
            Connection connection = null;
            try {
                connection = DatabaseConnection.getInstance().getConnection();

                if (connection != null && !isDatabaseInitialized(connection)) {
                    LoggerUtility.logInfo(logger, "Database is not initialized. Initializing now...");
                    DatabaseInitializer.runSQLScript(connection, "src/main/resources/db_setup.sql");
                    isInitialized = true; // Mark the database as initialized
                }
            } catch (Exception e) {
                LoggerUtility.logError(logger, "Database initialization failed", e);
            } finally {
                if (connection != null) {
                    DatabaseConnection.getInstance().disconnect();
                }
            }
        } else {
            LoggerUtility.logInfo(logger, "Database has already been initialized previously.");
        }
    }

    // Method to load and execute the SQL script
    private static void runSQLScript(Connection connection, String filePath) {
        StringBuilder sqlScript = new StringBuilder();

        // Read the SQL script from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sqlScript.append(line).append("\n");
            }
        } catch (IOException e) {
            LoggerUtility.logError(logger, "Error reading SQL script file", e);
            return;
        }

        // Execute the SQL script
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlScript.toString());
            LoggerUtility.logSuccess(logger, "SQL script executed successfully.");
        } catch (SQLException e) {
            LoggerUtility.logError(logger, "Error executing SQL script", e);
        }
    }
}
