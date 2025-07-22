package com.orangehrm.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.orangehrm.utilities.ScreenshotUtility;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseClass {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setUpExtentReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/OrangeHRM_ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Project", "OrangeHRM Automation");
        extent.setSystemInfo("Tester", "Komal");
        extent.setSystemInfo("Environment", "QA");
    }

    @BeforeMethod
    public void setUp(Method method) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Create ExtentTest instance for each test
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtility.captureScreenshot(driver, result.getName());
            test.fail(result.getThrowable());
            test.addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped");
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDownExtentReport() {
        extent.flush();
    }
}
