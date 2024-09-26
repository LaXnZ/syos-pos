package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;

public class ConfigLoader {

    private static final Logger logger = LoggerUtility.getLogger(ConfigLoader.class);
    private static Properties properties = new Properties();

    // Static block for loading the properties file
    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                // Log error if config.properties is not found
                LoggerUtility.logError(logger, "Sorry, unable to find config.properties", null);
            } else {
                // Load properties from the config file
                properties.load(input);
                // Log success message once the file is loaded
                LoggerUtility.logInfo(logger, "config.properties loaded successfully");
            }
        } catch (IOException ex) {
            // Log error if there is an issue while loading the file
            LoggerUtility.logError(logger, "Failed to load config.properties", ex);
        }
    }

    // Method to fetch a property by its key
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            // Log warning if the requested property is not found
            LoggerUtility.logWarn(logger, "Property '" + key + "' not found in config.properties");
        }
        return value;
    }
}
