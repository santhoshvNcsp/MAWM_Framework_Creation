package com.p09.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties properties = new Properties();

    static {

        try (InputStream inputStream = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("config/config.properties file not found.");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Unable to load config/config.properties", e);
        }

    }

    private ConfigManager() {
    }

    /**
     * Returns the property value as String.
     */
    public static String get(String key) {

        String value = properties.getProperty(key);

        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Missing configuration property : " + key);
        }

        return value.trim();
    }

    /**
     * Returns the property value as Integer.
     */
    public static int getInt(String key) {

        try {
            return Integer.parseInt(get(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid integer value for property : " + key, e);
        }

    }

    /**
     * Returns the property value as Boolean.
     */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

}