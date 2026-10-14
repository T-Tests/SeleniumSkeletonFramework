package com.skeleton.tests;

import com.skeleton.base.BaseTest;
import com.skeleton.dataproviders.TestDataProviders;
import com.skeleton.pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CartTest extends BaseTest {

    @Test(dataProvider = "cartData", dataProviderClass = TestDataProviders.class,
            description = "Products added on the inventory page appear in the cart")
    public void addedProductsAppearInCart(Map<String, String> data) {
        List<String> products = Arrays.asList(data.get("products").split(";"));

        CartPage cart = loginPage.loginAs(validUser())
                .addToCart(products.toArray(new String[0]))
                .goToCart();

        Assert.assertEquals(cart.getItemNames(), products, "Cart items differ from added products");
        Assert.assertEquals(cart.getHeaderCartCount(), products.size(), "Cart badge count is wrong");
    }
}