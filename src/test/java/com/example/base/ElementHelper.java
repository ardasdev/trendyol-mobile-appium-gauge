package com.example.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class ElementHelper {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public ElementHelper(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void click(String key) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(ElementRepository.by(key)));
        el.click();
    }

    public void type(String key, String value) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(ElementRepository.by(key)));
        el.sendKeys(value);
    }

    public boolean isVisible(String key) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(ElementRepository.by(key)));
        return el.isDisplayed();
    }

    public String getText(String key) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ElementRepository.by(key))).getText();
    }

    public boolean textContains(String key, String beklenen) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(ElementRepository.by(key), beklenen));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isChecked(String key) {
        try {
            wait.until(d -> "true".equals(d.findElement(ElementRepository.by(key)).getAttribute("checked")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPresent(String key, int saniye) {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        try {
            long bitis = System.currentTimeMillis() + saniye * 1000L;
            while (System.currentTimeMillis() < bitis) {
                if (!driver.findElements(ElementRepository.by(key)).isEmpty()) {
                    return true;
                }
            }
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    public void varsaKapat(String key) {

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        try {
            long beklemeMs = 5000;
            for (int i = 0; i < 3; i++) {
                long bitis = System.currentTimeMillis() + beklemeMs;
                boolean bulundu = false;
                while (System.currentTimeMillis() < bitis) {
                    if (!driver.findElements(ElementRepository.by(key)).isEmpty()) {
                        bulundu = true;
                        break;
                    }
                }
                if (!bulundu) {
                    break;
                }
                driver.navigate().back();
                beklemeMs = 2000;
            }
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    public boolean isPackageOpened(String paket) {
        AndroidDriver androidDriver = (AndroidDriver) driver;
        try {
            wait.until(d -> {
                String pkg = androidDriver.getCurrentPackage();
                return pkg != null && pkg.contains(paket);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void pressKey(int keycode) {
        driver.executeScript("mobile: pressKey", Map.of("keycode", keycode));
    }

    public void scrollDown() {
        driver.executeScript("mobile: scrollGesture", Map.of(
                "left", 100, "top", 500, "width", 880, "height", 1400,
                "direction", "down",
                "percent", 0.8));
    }

    public boolean scrollToElement(String key, int maxKaydirma) {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        try {
            for (int i = 0; i < maxKaydirma; i++) {
                if (!driver.findElements(ElementRepository.by(key)).isEmpty()) {
                    return true;
                }
                scrollDown();
            }
            return !driver.findElements(ElementRepository.by(key)).isEmpty();
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }
}
