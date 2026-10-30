package com.example.utils;

import java.io.InputStream;
import java.util.Properties;


public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input =
                     ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties bulunamadı src/test/resources içinde mi?");
            }
            PROPERTIES.load(input);
        } catch (Exception e) {
            throw new RuntimeException("config.properties okunamadı", e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new RuntimeException("config.properties içinde '" + key + "' anahtarı yok");
        }
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
