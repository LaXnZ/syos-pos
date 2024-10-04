package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionTest {

    private Connection connection;

    public DBConnectionTest() {
        // Load test database credentials from test.properties file
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("test.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find test.properties");
                return;
            }

            // Load properties file
            properties.load(input);

            // Extract database credentials
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            // Establish connection
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
