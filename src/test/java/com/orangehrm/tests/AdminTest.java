package com.orangehrm.tests;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.utilities.ScreenshotUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminTest extends BaseClass {

    @Test
    public void testAdminUserCRUD() {
        test = extent.createTest("Admin Module - User CRUD Operations");

        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.loginToApp("Admin", "admin123");
        Assert.assertTrue(dashboardPage.isDashboardVisible(), "Login failed or Dashboard not visible");

        // Step 2: Navigate to Admin tab
        AdminPage adminPage = new AdminPage(driver);
        adminPage.goToAdminTab();

        // Step 3: Search for existing user
        String existingUsername = "Admin";
        adminPage.searchUser(existingUsername);

        // Step 4: Assert existing user appears
        boolean found = adminPage.isUserInTable(existingUsername);
        Assert.assertTrue(found, "❌ User '" + existingUsername + "' not found in table after search!");

        // Step 5: Add new user
        String newUsername = adminPage.addUser("Admin", "Enabled", "Paul Collings");

        // Step 6: Search for new user
        adminPage.searchUser(newUsername);
        boolean newUserFound = adminPage.isUserInTable(newUsername);
        Assert.assertTrue(newUserFound, "❌ Newly added user '" + newUsername + "' not found!");

        // Step 7: Edit user
        String updatedUsername = "updated_" + newUsername;
        adminPage.editUser(updatedUsername);

        // Step 8: Search updated user
        adminPage.searchUser(updatedUsername);
        boolean updatedUserFound = adminPage.isUserInTable(updatedUsername);
        Assert.assertTrue(updatedUserFound, "❌ Updated user '" + updatedUsername + "' not found!");

        // Step 9: Delete updated user
        adminPage.deleteUser();

        // Step 10: Confirm deletion
        adminPage.searchUser(updatedUsername);
        boolean isDeleted = !adminPage.isUserInTable(updatedUsername);
        Assert.assertTrue(isDeleted, "❌ Deleted user '" + updatedUsername + "' still appears in table!");

        // Final screenshot
        ScreenshotUtility.captureScreenshot(driver, "testAdminUserCRUD");
    }
}
