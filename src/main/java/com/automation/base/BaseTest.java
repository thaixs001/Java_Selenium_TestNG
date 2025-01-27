package com.automation.base;

import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.getConfig("browser").asText();
        boolean headless = ConfigReader.getConfig("headless").asBoolean();
        driver = DriverManager.initializeDriver(browser, headless);

        int implicitWait = ConfigReader.getConfig("timeout").get("implicitWait").asInt();
        DriverManager.setImplicitWait(driver, implicitWait);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
