package com.automation.base;

import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class BaseTest {
    protected WebDriver driver;

    // Thêm phương thức này để xóa file trong thư mục screenshots
    @BeforeSuite
    public void clearScreenshotsFolder() {
        String screenshotsPath = ConfigReader.getConfig("filePaths").get("screenshots").asText();
        File folder = new File(screenshotsPath);
        
        // Kiểm tra xem thư mục có tồn tại không
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) {
                        System.err.println("Không thể xóa file: " + file.getName());
                    }
                }
            }
            System.out.println("Đã xóa tất cả file trong thư mục screenshots.");
        } else {
            System.out.println("Thư mục screenshots không tồn tại, không cần xóa.");
        }
    }

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.getConfig("browser").asText();
        boolean headless = ConfigReader.getConfig("headless").asBoolean();
        driver = DriverManager.initializeDriver(browser, headless);

        int implicitWait = ConfigReader.getConfig("timeout").get("implicitWait").asInt();
        DriverManager.setImplicitWait(driver, implicitWait);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getName();
            ScreenshotUtil.takeScreenshot(driver, testName + "_FAILED");
        }

        if (driver != null) {
            driver.quit();
        }

        System.out.println("Test '" + result.getName() + "' đã kết thúc với trạng thái: " + getTestStatus(result));
    }

    @AfterSuite
    public void afterSuite() {
        boolean shouldOpenReport = ConfigReader.getConfig("openExtentReport").asBoolean();
        if (shouldOpenReport) {
            openExtentReport();
        } else {
            System.out.println("Cấu hình không yêu cầu mở ExtentReport.");
        }
    }

    private String getTestStatus(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS: return "PASSED";
            case ITestResult.FAILURE: return "FAILED";
            case ITestResult.SKIP: return "SKIPPED";
            default: return "UNKNOWN";
        }
    }

    private void openExtentReport() {
        String reportPath = "test-output/ExtentReport.html";
        File reportFile = new File(reportPath);

        if (reportFile.exists()) {
            try {
                Desktop.getDesktop().browse(reportFile.toURI());
            } catch (IOException e) {
                System.err.println("Không thể mở file báo cáo: " + e.getMessage());
            }
        } else {
            System.err.println("File báo cáo không tồn tại: " + reportPath);
        }
    }
}