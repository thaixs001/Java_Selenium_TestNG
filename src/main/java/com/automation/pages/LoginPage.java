package com.automation.pages;

import com.automation.locators.LoginLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        driver.findElement(By.xpath(LoginLocators.USERNAME_FIELD)).sendKeys(username);
        driver.findElement(By.xpath(LoginLocators.PASSWORD_FIELD)).sendKeys(password);
        driver.findElement(By.xpath(LoginLocators.LOGIN_BUTTON)).click();
    }

    public String getTitleText() {
        return driver.findElement(By.xpath(LoginLocators.TITLE_MAIN)).getText();
    }
}
