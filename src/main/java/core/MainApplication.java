package core;

import database.DBInitializer;

public class MainApplication {
    public static void main(String[] args) {
        System.out.println("Starting the SYOS POS application...");

        // Initialize the database
        DBInitializer.initializeDatabase();

        // Proceed with the rest of your application logic
        System.out.println("Application started.");
    }
}
