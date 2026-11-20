package com.qa.framework.ui;

import com.qa.framework.base.BaseUiTest;
import com.qa.framework.config.ConfigReader;
import com.qa.framework.driver.DriverFactory;
import com.qa.framework.pages.InventoryPage;
import com.qa.framework.pages.LoginPage;
import com.qa.framework.utils.JsonUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenLoginTest extends BaseUiTest {

    @DataProvider(name = "users")
    public Object[][] users() {
        JSONArray array = JsonUtils.readArray("users.json");
        Object[][] data = new Object[array.size()][1];
        for (int i = 0; i < array.size(); i++) {
            data[i][0] = array.get(i);
        }
        return data;
    }

    @Test(dataProvider = "users", description = "Login outcome matches users.json")
    public void loginFromJson(JSONObject user) {
        LoginPage login = new LoginPage(DriverFactory.getDriver())
                .open(ConfigReader.get("ui.base.url"));
        InventoryPage inventory = login.loginAs((String) user.get("username"), (String) user.get("password"));

        String expected = (String) user.get("expected");
        if ((Boolean) user.get("valid")) {
            Assert.assertEquals(inventory.title(), expected);
        } else {
            Assert.assertTrue(login.errorMessage().contains(expected),
                    "Unexpected error text: " + login.errorMessage());
        }
    }
}
