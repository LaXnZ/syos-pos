package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtility {

    // ANSI escape codes for color formatting
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m"; // Custom color for normal info

    // Private constructor to prevent instantiation
    private LoggerUtility() {}

    // Generic method to create and return a logger for the calling class
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    // Method to log errors in red (Error level)
    public static void logError(Logger logger, String message, Throwable throwable) {
        logger.error(ANSI_RED + "[ERROR] " + message + ANSI_RESET, throwable);
    }

    // Method to log success messages in green
    public static void logSuccess(Logger logger, String message) {
        logger.info(ANSI_GREEN + "[SUCCESS] " + message + ANSI_RESET);
    }

    // Method to log warnings in yellow
    public static void logWarn(Logger logger, String message) {
        logger.warn(ANSI_YELLOW + "[WARNING] " + message + ANSI_RESET);
    }

    // Method to log general info messages in blue
    public static void logInfo(Logger logger, String message) {
        logger.info(ANSI_BLUE + "[INFO] " + message + ANSI_RESET);
    }

    // Method to log debug messages in blue (similar to info)
    public static void logDebug(Logger logger, String message) {
        logger.debug(ANSI_BLUE + "[DEBUG] " + message + ANSI_RESET);
    }
}
