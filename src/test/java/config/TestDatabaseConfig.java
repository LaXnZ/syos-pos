package config;

import utils.TestConfigLoader;

public class TestDatabaseConfig {

    // Load test-specific database configuration properties
    public static String getUrl() {
        return TestConfigLoader.getTestProperty("db.url");
    }

    public static String getUsername() {
        return TestConfigLoader.getTestProperty("db.user");
    }

    public static String getPassword() {
        return TestConfigLoader.getTestProperty("db.password");
    }
}
