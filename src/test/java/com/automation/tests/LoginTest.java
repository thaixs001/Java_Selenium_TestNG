package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testValidLogin() {
        String environment = ConfigReader.getConfig("environment").asText();

        String username = TestDataReader.getTestData("dataLogin").get(environment).get("username").asText();
        String password = TestDataReader.getTestData("dataLogin").get(environment).get("password").asText();
        String url = TestDataReader.getTestData("systemName").get(environment).get("url").asText();

        driver.get(url);
        System.out.println(driver.getTitle());

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        String expectedTitle = "Swag Labs";
        String actualTitle = loginPage.getTitleText();
        Assert.assertEquals(actualTitle, expectedTitle, "Title does not match after login.");
        
    }
}
