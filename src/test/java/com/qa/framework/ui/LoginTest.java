package com.qa.framework.ui;

import com.qa.framework.base.BaseUiTest;
import com.qa.framework.config.ConfigReader;
import com.qa.framework.driver.DriverFactory;
import com.qa.framework.pages.InventoryPage;
import com.qa.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseUiTest {

    @Test(description = "A valid user lands on the products page")
    public void validLogin() {
        InventoryPage inventory = new LoginPage(DriverFactory.getDriver())
                .open(ConfigReader.get("ui.base.url"))
                .loginAs(ConfigReader.get("username"), ConfigReader.get("password"));

        Assert.assertEquals(inventory.title(), "Products");
        Assert.assertEquals(inventory.itemCount(), 6);
    }

    @Test(description = "A wrong password shows an error and stays on the login page")
    public void invalidPassword() {
        LoginPage login = new LoginPage(DriverFactory.getDriver())
                .open(ConfigReader.get("ui.base.url"));
        login.loginAs(ConfigReader.get("username"), "wrong_password");

        Assert.assertTrue(login.errorMessage().contains("do not match"),
                "Unexpected error text: " + login.errorMessage());
    }

    @Test(description = "Adding an item updates the cart badge")
    public void addToCart() {
        InventoryPage inventory = new LoginPage(DriverFactory.getDriver())
                .open(ConfigReader.get("ui.base.url"))
                .loginAs(ConfigReader.get("username"), ConfigReader.get("password"))
                .addFirstItemToCart();

        Assert.assertEquals(inventory.cartCount(), "1");
    }
}
