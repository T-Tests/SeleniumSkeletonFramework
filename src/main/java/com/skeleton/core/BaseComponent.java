package com.skeleton.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * COMPOSITION: a reusable part of the UI (header, menu, footer) that appears on many pages.
 * Components are searched inside their own root element, so locators stay short and scoped.
 */
public abstract class BaseComponent implements Loadable {

    protected final WebDriver driver;
    private final By rootLocator;

    protected BaseComponent(WebDriver driver, By rootLocator) {
        this.driver = driver;
        this.rootLocator = rootLocator;
    }

    protected WebElement root() {
        return driver.findElement(rootLocator);
    }

    protected WebElement find(By locator) {
        return root().findElement(locator);
    }

    @Override
    public boolean isLoaded() {
        return !driver.findElements(rootLocator).isEmpty() && root().isDisplayed();
    }
}