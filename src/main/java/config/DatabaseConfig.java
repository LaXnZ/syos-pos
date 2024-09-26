package config;

import utils.ConfigLoader;

public class DatabaseConfig {
    public static String getUrl() {
        return ConfigLoader.getProperty("db.url");
    }

    public static String getUsername() {
        return ConfigLoader.getProperty("db.username");
    }

    public static String getPassword() {
        return ConfigLoader.getProperty("db.password");
    }
}
