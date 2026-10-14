package com.skeleton.core;

import com.skeleton.config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * ABSTRACTION + INHERITANCE: every page extends this. It owns the driver, the explicit waits and
 * the common element actions, so page classes only contain locators and business methods.
 * <p>
 * Enhanced POM rules used in this framework:
 * 1. Locators are private to the page (encapsulation); tests never see By/WebElement.
 * 2. Page methods return the next page object (fluent navigation).
 * 3. Page methods never assert; tests do. Pages expose state through getters/isXxx methods.
 * 4. Repeated UI parts (header, menus) are components, shared by composition.
 */
public abstract class BasePage implements Loadable {

    protected final WebDriver driver;
    private final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        int seconds = ConfigManager.getInstance().getInt("explicit.wait.seconds");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    /** Each page defines what "I am displayed" means. */
    @Override
    public abstract boolean isLoaded();

    /** Human-readable page name, handy for report messages. */
    public abstract String getPageName();

    // ---- POLYMORPHISM (method overloading): same action, different input types ----
    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForVisible(locator).getText().trim();
    }

    protected List<String> getTexts(By locator) {
        return driver.findElements(locator).stream().map(e -> e.getText().trim()).toList();
    }

    protected void selectByVisibleText(By locator, String text) {
        new Select(waitForVisible(locator)).selectByVisibleText(text);
    }

    protected boolean isDisplayed(By locator) {
        return !driver.findElements(locator).isEmpty() && driver.findElement(locator).isDisplayed();
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** GENERICS: waits until the page reports loaded and returns it as its own type for chaining. */
    @SuppressWarnings("unchecked")
    public <T extends BasePage> T waitUntilLoaded() {
        wait.until(d -> isLoaded());
        return (T) this;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}