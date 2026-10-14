package com.skeleton.enums;

import com.skeleton.exceptions.FrameworkException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * ENUM + POLYMORPHISM: each constant overrides the abstract createDriver method.
 * To support a new browser, add one constant - nothing else changes (Open/Closed principle).
 */
public enum BrowserType {

    CHROME {
        @Override
        public WebDriver createDriver(boolean headless) {
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            return new ChromeDriver(options);
        }
    },
    FIREFOX {
        @Override
        public WebDriver createDriver(boolean headless) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("-headless");
            }
            return new FirefoxDriver(options);
        }
    },
    EDGE {
        @Override
        public WebDriver createDriver(boolean headless) {
            EdgeOptions options = new EdgeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            return new EdgeDriver(options);
        }
    };

    public abstract WebDriver createDriver(boolean headless);

    public static BrowserType from(String name) {
        try {
            return valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new FrameworkException("Unsupported browser: " + name, e);
        }
    }
}