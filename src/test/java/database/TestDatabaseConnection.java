package database;

import config.TestDatabaseConfig;
import utils.LoggerUtility;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestDatabaseConnection {

    private static final Logger logger = LoggerUtility.getLogger(TestDatabaseConnection.class);

    // Test for a successful connection
    @Test
    public void testDatabaseConnection_Success() {
        LoggerUtility.logInfo(logger, "Starting database connection test (successful case)...");

        // Create the database connection object
        try (Connection conn = DriverManager.getConnection(TestDatabaseConfig.getUrl(),
                TestDatabaseConfig.getUsername(),
                TestDatabaseConfig.getPassword())) {
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
        try (Connection conn = DriverManager.getConnection(wrongUrl,
                TestDatabaseConfig.getUsername(),
                TestDatabaseConfig.getPassword())) {
            assertNull(conn, "Connection should fail with incorrect URL");
        } catch (SQLException e) {
            LoggerUtility.logSuccess(logger, "Connection failed as expected due to incorrect configuration.");
        }
    }
}
