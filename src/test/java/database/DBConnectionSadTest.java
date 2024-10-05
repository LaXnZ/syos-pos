package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionSadTest {

    private DBConnection dbConnection;
    private Connection connection;

    @BeforeEach
    public void setUp() {

        dbConnection = new DBConnection();
    }

    @AfterEach
    public void tearDown() {

        if (connection != null) {
            dbConnection.closeConnection();
        }
    }


    @Test
    public void testConnectionFailure() {

        try {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();


            assertNull(connection, "Connection should be null if connection failed.");
        } catch (Exception e) {

            assertNotNull(e.getMessage(), "Exception should be thrown for invalid connection");
            System.out.println("Connection failure test passed. Error: " + e.getMessage());
        }
    }

    @Test
    public void testConnectionFailureOnClose() {

        try {
            dbConnection.closeConnection();
            dbConnection.closeConnection();
            assertTrue(true, "Closing an already closed connection should not throw an exception.");
        } catch (Exception e) {
            fail("Exception should not be thrown when closing an already closed connection.");
        }
    }
}
