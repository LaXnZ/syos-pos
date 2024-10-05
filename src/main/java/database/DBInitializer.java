package database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DBInitializer {

    private final DBConnection dbConnection;
    private Connection connection;

    public DBInitializer(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // execute the sql script to initialize the database if tables are not exists
    public void initializeDatabase() {
        try {
            connection = dbConnection.getConnection();
            Statement statement = connection.createStatement();

            // check if the tables are already exist in the database
            if (!areTablesPresent()) {

                String sql = loadSQLFile("create_all_tables.sql");
                if (sql != null) {
                    statement.execute(sql);
                    System.out.println("Database tables initialized successfully.");
                } else {
                    System.out.println("Error: dbSetup.sql file not found.");
                }
            } else {
                System.out.println("Database tables already exist. No need to initialize.");
            }
        } catch (SQLException e) {
            System.out.println("Error initializing the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // check if the tables are already exist in the database
    private boolean areTablesPresent() throws SQLException {
        String checkQuery = "SELECT table_name FROM information_schema.tables WHERE table_schema='public'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(checkQuery);
        return resultSet.next();  // If there are any tables, it will return true
    }

    // load the SQL file from the resources folder
    private String loadSQLFile(String fileName) {
        try (InputStream input = DBInitializer.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            System.out.println("Error loading SQL file: " + e.getMessage());
            return null;
        }
    }
}
