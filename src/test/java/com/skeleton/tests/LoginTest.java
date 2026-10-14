package com.skeleton.tests;

import com.skeleton.base.BaseTest;
import com.skeleton.dataproviders.TestDataProviders;
import com.skeleton.models.User;
import com.skeleton.pages.InventoryPage;
import com.skeleton.reporting.TestListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = TestDataProviders.class,
            description = "Login with valid and invalid credentials from Excel")
    public void loginScenarios(Map<String, String> data) {
        User user = new User(data.get("username"), data.get("password"));
        TestListener.log("Logging in as " + user);

        if (data.get("expectedResult").equalsIgnoreCase("success")) {
            InventoryPage inventory = loginPage.loginAs(user);
            Assert.assertTrue(inventory.isLoaded(), "Inventory page should be displayed");
        } else {
            String error = loginPage.loginExpectingFailure(user).getErrorMessage();
            Assert.assertTrue(error.contains(data.get("expectedMessage")),
                    "Unexpected error message: " + error);
        }
    }

    @Test(description = "User can log out and return to the login page")
    public void logoutReturnsToLogin() {
        boolean onLogin = loginPage.loginAs(validUser()).logout().isLoaded();
        Assert.assertTrue(onLogin, "Login page should be displayed after logout");
    }
}