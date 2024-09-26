package app;

import database.DatabaseInitializer;
import utils.LoggerUtility;
import org.slf4j.Logger;

public class MainApplication {

    private static final Logger logger = LoggerUtility.getLogger(MainApplication.class);

    public static void main(String[] args) {
        LoggerUtility.logInfo(logger, "Starting SYOS POS application...");

        // Initialize the database (only if not already initialized)
        DatabaseInitializer.getInstance().initializeDatabase();

        LoggerUtility.logInfo(logger, "Database is initialized and ready.");

        // Continue with other application logic...
        runApplication();
    }

    private static void runApplication() {
        LoggerUtility.logInfo(logger, "POS system running. Awaiting customer transactions...");
    }
}
