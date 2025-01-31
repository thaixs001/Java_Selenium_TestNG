package com.automation.base;

import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

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
    public void tearDown(ITestResult result) {
        // Chụp màn hình nếu test fail
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getName(); // Lấy tên của test method
            ScreenshotUtil.takeScreenshot(driver, testName + "_FAILED");
        }

        // Đóng trình duyệt
        if (driver != null) {
            driver.quit();
        }

        // Ghi log kết quả test
        System.out.println("Test '" + result.getName() + "' đã kết thúc với trạng thái: " + getTestStatus(result));
    }

    @AfterSuite
    public void afterSuite() {
        // Kiểm tra config để quyết định có mở báo cáo không
        boolean shouldOpenReport = ConfigReader.getConfig("openExtentReport").asBoolean();
        
        if (shouldOpenReport) {
            openExtentReport();
        } else {
            System.out.println("Cấu hình không yêu cầu mở ExtentReport.");
        }
    }

    // Phương thức hỗ trợ để lấy trạng thái test
    private String getTestStatus(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                return "PASSED";
            case ITestResult.FAILURE:
                return "FAILED";
            case ITestResult.SKIP:
                return "SKIPPED";
            default:
                return "UNKNOWN";
        }
    }

    // Phương thức mở báo cáo ExtentReport
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