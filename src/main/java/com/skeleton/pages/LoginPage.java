package com.skeleton.pages;

import com.skeleton.config.ConfigManager;
import com.skeleton.core.BasePage;
import com.skeleton.models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By USERNAME = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Opens the application and returns the login page. */
    public LoginPage open() {
        driver.get(ConfigManager.getInstance().baseUrl());
        return waitUntilLoaded();
    }

    /** Successful login: returns the next page (fluent navigation). */
    public InventoryPage loginAs(User user) {
        submit(user);
        return new InventoryPage(driver).waitUntilLoaded();
    }

    /** Failed login: stays on this page so the test can read the error. */
    public LoginPage loginExpectingFailure(User user) {
        submit(user);
        waitForVisible(ERROR_MESSAGE);
        return this;
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }

    private void submit(User user) {
        type(USERNAME, user.getUsername());
        type(PASSWORD, user.getPassword());
        click(LOGIN_BUTTON);
    }

    @Override
    public boolean isLoaded() {
        return isDisplayed(LOGIN_BUTTON);
    }

    @Override
    public String getPageName() {
        return "Login Page";
    }
}