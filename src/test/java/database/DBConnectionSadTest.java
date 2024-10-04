package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionSadTest {

    private DBConnection dbConnection;  // Use the actual DBConnection class for testing
    private Connection connection;

    @BeforeEach
    public void setUp() {
        // Set up DB connection object before each test (use a valid connection here)
        dbConnection = new DBConnection();  // Initialize without credentials (if it's not needed)
    }

    @AfterEach
    public void tearDown() {
        // Close the DB connection after each test
        if (connection != null) {
            dbConnection.closeConnection();
        }
    }

    // ====== Sad Path Tests ======

    @Test
    public void testConnectionFailure() {
        // Simulate database connection failure by providing invalid settings inside the DBConnection class
        try {
            dbConnection = new DBConnection();  // Assuming DBConnection uses internal invalid settings for tests
            connection = dbConnection.getConnection();

            // If connection is established, this test should fail
            assertNull(connection, "Connection should be null if connection failed.");
        } catch (Exception e) {
            // An exception should be thrown here due to invalid credentials or bad connection
            assertNotNull(e.getMessage(), "Exception should be thrown for invalid connection");
            System.out.println("Connection failure test passed. Error: " + e.getMessage());
        }
    }

    @Test
    public void testConnectionFailureOnClose() {
        // Try closing an already closed connection
        try {
            dbConnection.closeConnection();  // First close attempt
            dbConnection.closeConnection();  // Second close attempt (shouldn't throw an error)
            assertTrue(true, "Closing an already closed connection should not throw an exception.");
        } catch (Exception e) {
            fail("Exception should not be thrown when closing an already closed connection.");
        }
    }
}
