package com.qa.framework.base;

import com.qa.framework.driver.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseUiTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.start();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quit();
    }
}
