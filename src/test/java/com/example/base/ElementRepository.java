package com.example.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class ElementRepository {

    private static final Map<String, By> LOCATORS = new HashMap<>();

    static {
        try (InputStream input =
                     ElementRepository.class.getClassLoader().getResourceAsStream("elements.json")) {
            if (input == null) {
                throw new RuntimeException("elements.json bulunamadı! src/test/resources içinde mi?");
            }
            JsonNode nodes = new ObjectMapper().readTree(input);
            for (JsonNode node : nodes) {
                String key   = node.get("key").asText();
                String value = node.get("value").asText();
                String type  = node.get("type").asText().toLowerCase();
                LOCATORS.put(key, toBy(type, value));
            }
        } catch (Exception e) {
            throw new RuntimeException("elements.json okunamadı", e);
        }
    }

    private ElementRepository() {}

    public static By by(String key) {
        By locator = LOCATORS.get(key);
        if (locator == null) {
            throw new IllegalArgumentException("elements.json içinde '" + key + "' anahtarı bulunamadı");
        }
        return locator;
    }

    private static By toBy(String type, String value) {
        return switch (type) {
            case "accessibility" -> AppiumBy.accessibilityId(value);
            case "xpath"         -> By.xpath(value);
            case "id"            -> By.id(value);
            case "class"         -> By.className(value);
            case "uiautomator"   -> AppiumBy.androidUIAutomator(value);
            default -> throw new IllegalArgumentException("Bilinmeyen locator tipi: " + type);
        };
    }
}
