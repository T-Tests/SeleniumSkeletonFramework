package com.skeleton.pages;

import com.skeleton.components.HeaderComponent;
import com.skeleton.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class InventoryPage extends BasePage {

    private static final By TITLE = By.className("title");
    private static final By PRODUCT_NAMES = By.className("inventory_item_name");
    private static final By SORT_DROPDOWN = By.className("product_sort_container");

    // Dynamic locator template: %s is replaced with the product name
    private static final String ADD_BUTTON_FOR =
            "//div[text()='%s']/ancestor::div[@class='inventory_item']//button";

    private final HeaderComponent header; // COMPOSITION

    public InventoryPage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver);
    }

    public HeaderComponent header() {
        return header;
    }

    public InventoryPage addToCart(String productName) {
        click(By.xpath(String.format(ADD_BUTTON_FOR, productName)));
        return this;
    }

    /** Varargs overload: add several products in one call. */
    public InventoryPage addToCart(String... productNames) {
        for (String name : productNames) {
            addToCart(name);
        }
        return this;
    }

    public InventoryPage sortBy(String visibleText) {
        selectByVisibleText(SORT_DROPDOWN, visibleText);
        return this;
    }

    public List<String> getProductNames() {
        return getTexts(PRODUCT_NAMES);
    }

    public CartPage goToCart() {
        header.openCart();
        return new CartPage(driver).waitUntilLoaded();
    }

    public LoginPage logout() {
        header.logout();
        return new LoginPage(driver).waitUntilLoaded();
    }

    @Override
    public boolean isLoaded() {
        return isDisplayed(TITLE) && getText(TITLE).equals("Products");
    }

    @Override
    public String getPageName() {
        return "Inventory Page";
    }
}