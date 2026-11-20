package com.qa.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {

    private static final By TITLE = By.cssSelector(".title");
    private static final By ITEMS = By.cssSelector(".inventory_item");
    private static final By CART_BADGE = By.cssSelector(".shopping_cart_badge");
    private static final By ADD_FIRST_ITEM = By.xpath("(//button[contains(@data-test,'add-to-cart')])[1]");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String title() {
        return textOf(TITLE);
    }

    public int itemCount() {
        visible(ITEMS);
        return driver.findElements(ITEMS).size();
    }

    public InventoryPage addFirstItemToCart() {
        click(ADD_FIRST_ITEM);
        return this;
    }

    public String cartCount() {
        return textOf(CART_BADGE);
    }
}
