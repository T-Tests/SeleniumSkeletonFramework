package com.skeleton.pages;

import com.skeleton.components.HeaderComponent;
import com.skeleton.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CartPage extends BasePage {

    private static final By TITLE = By.className("title");
    private static final By ITEM_NAMES = By.className("inventory_item_name");

    private final HeaderComponent header;

    public CartPage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver);
    }

    public List<String> getItemNames() {
        return getTexts(ITEM_NAMES);
    }

    public int getHeaderCartCount() {
        return header.getCartCount();
    }

    @Override
    public boolean isLoaded() {
        return isDisplayed(TITLE) && getText(TITLE).equals("Your Cart");
    }

    @Override
    public String getPageName() {
        return "Cart Page";
    }
}