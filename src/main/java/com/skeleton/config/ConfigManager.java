package com.skeleton.config;

import com.skeleton.exceptions.FrameworkException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * SINGLETON + ENCAPSULATION: one instance loads config.properties; the Properties stay private
 * and are exposed through typed getters. JVM system properties (-Dkey=value) take priority.
 */
public final class ConfigManager {

    private static final ConfigManager INSTANCE = new ConfigManager();
    private final Properties properties = new Properties();

    private ConfigManager() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new FrameworkException("config.properties not found on classpath");
            }
            properties.load(in);
        } catch (IOException e) {
            throw new FrameworkException("Unable to read config.properties", e);
        }
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    public String get(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null) {
            throw new FrameworkException("Missing config key: " + key);
        }
        return value.trim();
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public String baseUrl() {
        return get("base.url");
    }
}