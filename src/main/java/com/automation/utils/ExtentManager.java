package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    public static void openReport() {
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