package com.orangehrm.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/ExtentReport.html");
            reporter.config().setReportName("OrangeHRM Automation Report");
            reporter.config().setDocumentTitle("OrangeHRM Test Results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Komal Rasal");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }
}
