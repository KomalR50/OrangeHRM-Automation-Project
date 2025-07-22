package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    WebDriver driver;
    WebDriverWait wait;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By dashboardHeading = By.xpath("//h6[normalize-space()='Dashboard']");
    private By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
    private By logoutLink = By.xpath("//a[text()='Logout']");

    public boolean isDashboardVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeading)).isDisplayed();
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }
}
