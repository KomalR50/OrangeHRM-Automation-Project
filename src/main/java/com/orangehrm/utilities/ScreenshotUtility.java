package com.orangehrm.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtility {

    public static String captureScreenshot(WebDriver driver, String name) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName = name + "_" + timestamp + ".png";
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        File dest = new File(screenshotDir + screenshotName);

        try {
            FileUtils.copyFile(src, dest);
            System.out.println("📸 Screenshot saved at: " + dest.getAbsolutePath());
            return dest.getAbsolutePath(); // ✅ Return the screenshot path
        } catch (IOException e) {
            System.out.println("❌ Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }
}
