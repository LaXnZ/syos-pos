package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionHappyTest {

    private DBConnection dbConnection;
    private Connection connection;


    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("test.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return null;
            }

            properties.load(input);
        } catch (Exception e) {
            System.out.println("Error loading application properties: " + e.getMessage());
            e.printStackTrace();
        }
        return properties;
    }


    public static Connection getConnection() {
        Connection connection = null;
        Properties properties = loadProperties();
        if (properties != null) {
            try {
                String url = properties.getProperty("db.url");
                String user = properties.getProperty("db.user");
                String password = properties.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to the database successfully.");
            } catch (SQLException e) {
                System.out.println("Error connecting to the database: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    @BeforeEach
    public void setUp() {

        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        assertNotNull(connection, "Connection should not be null when DB is accessible");
    }

    @AfterEach
    public void tearDown() {

        dbConnection.closeConnection();
    }



    @Test
    public void testConnectionSuccess() {

        Connection connection = dbConnection.getConnection();
        assertNotNull(connection, "Connection should not be null when DB is accessible");
        System.out.println("Database connection successful in the happy path test.");
    }

    @Test
    public void testCloseConnection() throws SQLException {

        dbConnection.closeConnection();
        assertTrue(connection.isClosed(), "Connection should be closed successfully");
        System.out.println("Connection closed successfully.");
    }

    @Test
    public void testConnectionAutoClose() throws SQLException {
        try (Connection conn = dbConnection.getConnection()) {
            assertNotNull(conn, "Connection should not be null when DB is accessible");
        } catch (SQLException e) {
            fail("Connection auto-close failed: " + e.getMessage());
        }

        System.out.println("Auto-close connection test passed.");
    }

    @Test
    public void testDoubleCloseConnection() throws SQLException {

        dbConnection.closeConnection();


        dbConnection.closeConnection();
        System.out.println("Double connection close test passed.");
    }
}
