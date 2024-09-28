package core;

import database.DBInitializer;
import database.DBConnection;

public class AppInitializer {

    public static void initializeApp() {
        System.out.println("Initializing the SYOS POS application...");

        // Create an instance of DBConnection and DBInitializer
        DBConnection dbConnectionManager = new DBConnection();
        DBInitializer dbInitializer = new DBInitializer(dbConnectionManager);

        // Call the instance method to initialize the database
        dbInitializer.initializeDatabase();

        System.out.println("Application initialization complete.");
    }
}
