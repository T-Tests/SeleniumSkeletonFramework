package com.skeleton.base;

import com.skeleton.driver.DriverManager;
import com.skeleton.models.User;
import com.skeleton.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/** Parent of all test classes: starts a fresh browser for every test and always closes it. */
public abstract class BaseTest {

    protected LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.initDriver();
        loginPage = new LoginPage(DriverManager.getDriver()).open();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    /** Shared helper for tests that start from a logged-in state. */
    protected User validUser() {
        return new User("standard_user", "secret_sauce");
    }
}