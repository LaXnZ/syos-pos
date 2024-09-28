package core;

import database.DBInitializer;

public class AppInitializer {

    public static void initializeApp() {
        System.out.println("Initializing the SYOS POS application...");

        // Initialize the database (create tables if they don't exist)
        DBInitializer.initializeDatabase();

        System.out.println("Application initialization complete.");
    }
}
