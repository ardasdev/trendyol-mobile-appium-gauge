package com.example.base;

import com.example.utils.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URI;
import java.time.Duration;

public final class Driver {

    private static AndroidDriver driver;

    private Driver() {}

    public static AndroidDriver get() {
        if (driver == null) {
            try {
                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName(ConfigReader.get("platform.name"))
                        .setAutomationName(ConfigReader.get("automation.name"))
                        .setDeviceName(ConfigReader.get("device.name"))
                        .setNoReset(true);

                URI serverUri = URI.create(ConfigReader.get("appium.server.url"));
                driver = new AndroidDriver(serverUri.toURL(), options);
                driver.manage().timeouts().implicitlyWait(
                        Duration.ofSeconds(ConfigReader.getInt("implicit.wait.seconds")));
            } catch (Exception e) {
                throw new RuntimeException("AndroidDriver başlatılamadı", e);
            }
        }
        return driver;
    }

    public static void quit() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {

            } finally {
                driver = null;
            }
        }
    }
}
