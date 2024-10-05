package core;

import database.DBInitializer;
import database.DBConnection;

public class AppInitializer {

    public static void initializeApp() {
        System.out.println("Initializing the SYOS POS application...");

        // create a new instance of the DBConnection class
        DBConnection dbConnectionManager = new DBConnection();
        DBInitializer dbInitializer = new DBInitializer(dbConnectionManager);

        // call the initializeDatabase method of the DBInitializer class
        dbInitializer.initializeDatabase();

        System.out.println("Application initialization complete.");
    }
}
