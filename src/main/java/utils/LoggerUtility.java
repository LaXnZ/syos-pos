package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtility {

    // ANSI escape codes for color formatting
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";

    // Private constructor to prevent instantiation
    private LoggerUtility() {}

    // Generic method to create and return a logger for the calling class
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    // Method to log errors in red
    public static void logError(Logger logger, String message, Throwable throwable) {
        logger.error(ANSI_RED + message + ANSI_RESET, throwable);
    }

    // Method to log success messages in green
    public static void logSuccess(Logger logger, String message) {
        logger.info(ANSI_GREEN + message + ANSI_RESET);
    }

    // Method to log warnings in yellow
    public static void logWarn(Logger logger, String message) {
        logger.warn(ANSI_YELLOW + message + ANSI_RESET);
    }

    // Method to log general info (without color change)
    public static void logInfo(Logger logger, String message) {
        logger.info(message); // Default color
    }

    // Method to log debug messages (without color change)
    public static void logDebug(Logger logger, String message) {
        logger.debug(message); // Default color
    }
}
