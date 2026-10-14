package com.skeleton.components;

import com.skeleton.core.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Header shared by all post-login pages: menu, logout and cart badge. */
public class HeaderComponent extends BaseComponent {

    private static final By ROOT = By.id("header_container");
    private static final By MENU_BUTTON = By.id("react-burger-menu-btn");
    private static final By LOGOUT_LINK = By.id("logout_sidebar_link");
    private static final By CART_LINK = By.className("shopping_cart_link");
    private static final By CART_BADGE = By.className("shopping_cart_badge");

    public HeaderComponent(WebDriver driver) {
        super(driver, ROOT);
    }

    public void logout() {
        find(MENU_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK)).click();
    }

    public void openCart() {
        find(CART_LINK).click();
    }

    /** Returns 0 when the badge is absent (empty cart). */
    public int getCartCount() {
        return find(CART_LINK).findElements(CART_BADGE).stream()
                .findFirst().map(e -> Integer.parseInt(e.getText().trim())).orElse(0);
    }
}