package com.skeleton.driver;

import com.skeleton.config.ConfigManager;
import com.skeleton.enums.BrowserType;
import com.skeleton.exceptions.FrameworkException;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

/**
 * FACTORY + THREAD SAFETY: creates a driver per thread and stores it in a ThreadLocal so
 * parallel TestNG threads never share a browser. Static utility class: no instances allowed.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void initDriver() {
        ConfigManager config = ConfigManager.getInstance();
        BrowserType browser = BrowserType.from(config.get("browser"));
        WebDriver driver = browser.createDriver(config.getBoolean("headless"));
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getInt("page.load.timeout.seconds")));
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new FrameworkException("Driver not initialised. Call DriverManager.initDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}