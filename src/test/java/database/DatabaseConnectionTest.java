package database;

import utils.TestConfigLoader;
import utils.LoggerUtility;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DatabaseConnectionTest {

    private static final Logger logger = LoggerUtility.getLogger(DatabaseConnectionTest.class);

    // Load test-specific properties
    private static final String DB_URL = TestConfigLoader.getTestProperty("db.url");
    private static final String USER = TestConfigLoader.getTestProperty("db.user");
    private static final String PASSWORD = TestConfigLoader.getTestProperty("db.password");

    // Test for a successful connection
    @Test
    public void testDatabaseConnection_Success() {
        LoggerUtility.logInfo(logger, "Starting database connection test (successful case)...");

        // Create the database connection object
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            assertNotNull(conn, "The connection should be established");
            LoggerUtility.logSuccess(logger, "Connection successful!");
        } catch (SQLException e) {
            LoggerUtility.logError(logger, "Failed to connect to the database", e);
        }
    }

    // Test for a failed connection (e.g., invalid credentials or wrong URL)
    @Test
    public void testDatabaseConnection_Failure() {
        LoggerUtility.logInfo(logger, "Starting database connection test (failure case)...");

        // Simulate an incorrect database URL
        String wrongUrl = "jdbc:postgresql://localhost:5432/invalid_db";
        try (Connection conn = DriverManager.getConnection(wrongUrl, USER, PASSWORD)) {
            assertNull(conn, "Connection should fail with incorrect URL");
        } catch (SQLException e) {
            LoggerUtility.logWarn(logger, "Connection failed as expected due to incorrect configuration.");
        }
    }
}
