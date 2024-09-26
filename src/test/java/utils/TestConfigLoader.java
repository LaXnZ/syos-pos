package utils;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfigLoader {

    private static final Logger logger = LoggerUtility.getLogger(TestConfigLoader.class);
    private static Properties testProperties = new Properties();

    // Static block to load the properties from test-config.properties
    static {
        try (InputStream testInput = TestConfigLoader.class.getClassLoader().getResourceAsStream("test-config.properties")) {
            if (testInput == null) {
                LoggerUtility.logError(logger, "Unable to find test-config.properties", null);
            } else {
                testProperties.load(testInput);
                LoggerUtility.logInfo(logger, "test-config.properties loaded successfully");
            }
        } catch (IOException ex) {
            LoggerUtility.logError(logger, "Failed to load test-config.properties", ex);
        }
    }

    // Method to fetch properties from test-config.properties
    public static String getTestProperty(String key) {
        return testProperties.getProperty(key);
    }
}
