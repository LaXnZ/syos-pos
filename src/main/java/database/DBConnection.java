package database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection connection = null;

    // Load properties from application.properties
    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return null;
            }
            // Load properties file
            properties.load(input);
        } catch (Exception e) {
            System.out.println("Error loading application properties: " + e.getMessage());
            e.printStackTrace();
        }
        return properties;
    }

    // Get a connection to the database
    public static Connection getConnection() {
        if (connection == null) {
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
        }
        return connection;
    }

    // Close the database connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }
}
