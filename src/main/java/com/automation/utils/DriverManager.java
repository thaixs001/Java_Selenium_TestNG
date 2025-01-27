package com.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    public static WebDriver initializeDriver(String browser, boolean headless) {
        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless");
            }
            return new ChromeDriver(options);
        }
        throw new IllegalArgumentException("Browser not supported: " + browser);
    }

    public static void setImplicitWait(WebDriver driver, int timeout) {
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(timeout));
    }

    public static void maximizeWindow(WebDriver driver) {
        driver.manage().window().maximize();
    }
}
