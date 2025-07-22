package com.orangehrm.tests;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExcelUtility;
import com.orangehrm.utilities.ScreenshotUtility;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseClass {

    @DataProvider(name = "loginDataFromExcel")
    public Object[][] loginDataFromExcel() {
        String excelPath = System.getProperty("user.dir") + "/testdata/LoginData.xlsx";
        return ExcelUtility.readExcelData(excelPath, "Sheet1");
    }

    @Test(dataProvider = "loginDataFromExcel")
    public void testLogin(String username, String password) {
        System.out.println("🔍 Testing with username: '" + username + "', password: '" + password + "'");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (username.isEmpty() && password.isEmpty()) {
            // Blank fields check
            boolean userReqShown = loginPage.isUsernameRequiredDisplayed();
            boolean passReqShown = loginPage.isPasswordRequiredDisplayed();

            System.out.println("🟡 Username required: " + userReqShown);
            System.out.println("🟡 Password required: " + passReqShown);

            ScreenshotUtility.captureScreenshot(driver, "BlankFields_" + System.currentTimeMillis());
            Assert.assertTrue(userReqShown && passReqShown, "❌ Required messages not shown for blank fields.");

        } else if (username.equals("Admin") && password.equals("admin123")) {
            // Valid credentials
            Assert.assertTrue(loginPage.isDashboardVisible(), "❌ Valid login failed for: " + username);
            ScreenshotUtility.captureScreenshot(driver, "ValidLogin_" + System.currentTimeMillis());

            // Logout
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.logout();

        } else {
            // Invalid credentials
            boolean errorShown = loginPage.isErrorMessageDisplayed();
            System.out.println("🔎 Error message displayed: " + errorShown);

            ScreenshotUtility.captureScreenshot(driver, "InvalidLogin_" + System.currentTimeMillis());
            Assert.assertTrue(errorShown, "❌ Error message not shown for invalid login.");
        }
    }
}
