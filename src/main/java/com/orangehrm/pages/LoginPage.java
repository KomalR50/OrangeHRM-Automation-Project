package com.orangehrm.pages;

import com.orangehrm.utilities.ScreenshotUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By usernameRequired = By.xpath("//span[text()='Required' and ancestor::div[contains(@class, 'oxd-input-group')][.//input[@name='username']]]");
    private By passwordRequired = By.xpath("//span[text()='Required' and ancestor::div[contains(@class, 'oxd-input-group')][.//input[@name='password']]]");
    private By errorMessage = By.xpath("//p[contains(@class,'oxd-alert-content-text')]");
    private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
    private By logoutLink = By.xpath("//a[text()='Logout']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Actions
    public void enterUsername(String username) {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public boolean isUsernameRequiredDisplayed() {
        return driver.findElements(usernameRequired).size() > 0;
    }

    public boolean isPasswordRequiredDisplayed() {
        return driver.findElements(passwordRequired).size() > 0;
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElements(errorMessage).size() > 0;
    }

    public boolean isDashboardVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DashboardPage loginToApp(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new DashboardPage(driver);
    }

    public void logout() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink)).click();
        } catch (Exception e) {
            System.out.println("❌ Logout failed: " + e.getMessage());
            ScreenshotUtility.captureScreenshot(driver, "LogoutFailure_" + System.currentTimeMillis());
        }
    }
}
