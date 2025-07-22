package com.orangehrm.pages;
//private By statusDropdown = By.xpath("//div[contains(text(),'Enabled')]");         //         (//div[@class='oxd-select-text-input'])[2]
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class AdminPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By adminTab = By.xpath("//span[text()='Admin']");
    private By searchUsernameField = By.xpath("//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By addUserButton = By.xpath("//button[normalize-space()='Add']");
    private By roleDropdown = By.xpath("//label[text()='User Role']/..//div[contains(@class,'oxd-select-text')]");
    private By employeeNameField = By.xpath("//input[@placeholder='Type for hints...']");
    private By employeeDropdownOption = By.xpath("//div[@role='listbox']//span");
    private By usernameField = By.xpath("//label[text()='Username']/../following-sibling::div/input");
    private By passwordField = By.xpath("//label[text()='Password']/../following-sibling::div/input");
    private By confirmPasswordField = By.xpath("//label[text()='Confirm Password']/../following-sibling::div/input");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By userTableRows = By.xpath("//div[@class='oxd-table-body']//div[@role='row']");
    private By userNameCell = By.xpath(".//div[@role='cell'][2]"); // 2nd column is Username
    private By editIcon = By.xpath(".//button[1]");
    private By deleteIcon = By.xpath(".//button[2]");
    private By confirmDeleteButton = By.xpath("//button[normalize-space()='Yes, Delete']");

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToAdminTab() {
        System.out.println("👉 Navigating to Admin tab...");
        driver.findElement(adminTab).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
        System.out.println("✅ Admin tab opened.");
    }

    public void searchUser(String username) {
        System.out.println("🔍 Searching for user: " + username);
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchUsernameField));
        searchBox.clear();
        searchBox.sendKeys(username);
        driver.findElement(searchButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(userTableRows));
        System.out.println("✅ Search triggered.");
    }

    public boolean isUserInTable(String username) {
        List<WebElement> rows = driver.findElements(userTableRows);
        System.out.println("📋 Row count: " + rows.size());
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath(".//div[@role='cell']"));
            if (cells.size() > 1) {
                String usernameCell = cells.get(1).getText();
                System.out.println("🧪 Checking username cell: " + usernameCell);
                if (usernameCell.equalsIgnoreCase(username)) {
                    System.out.println("✅ Match found for username: " + username);
                    return true;
                }
            }
        }
        return false;
    }

    public String addUser(String role, String status, String employeeName) {
        System.out.println("➕ Adding new user...");

        driver.findElement(addUserButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(roleDropdown));

        System.out.println("➡️ Selecting role: " + role);
        driver.findElement(roleDropdown).click();
        String roleOptionXpath = "//div[@role='listbox']//span[text()='" + role + "']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(roleOptionXpath))).click();

        System.out.println("➡️ Typing employee name: " + employeeName + " (dynamically)");
        WebElement empField = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameField));
        empField.click();
        empField.clear();
        empField.sendKeys(employeeName.substring(0, 2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeDropdownOption)).click();

        String newUsername = "user" + UUID.randomUUID().toString().substring(0, 6);
        System.out.println("➡️ Entering username: " + newUsername);
        driver.findElement(usernameField).sendKeys(newUsername);

        System.out.println("➡️ Selecting status: " + status);
        selectStatus(status);

        driver.findElement(passwordField).sendKeys("Test@123");
        driver.findElement(confirmPasswordField).sendKeys("Test@123");

        System.out.println("💾 Saving new user...");
        driver.findElement(saveButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
        System.out.println("✅ User added successfully: " + newUsername);

        return newUsername;
    }

    public void selectStatus(String status) {
        WebElement statusDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[text()='Status']/..//div[contains(@class,'oxd-select-text')]")));
        statusDropdown.click();

        WebElement optionToSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@role='option']//span[text()='" + status + "']")));
        optionToSelect.click();
    }

    public void editUser(String newUsername) {
        System.out.println("✏️ Editing user to new username: " + newUsername);
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(userTableRows));
        editBtn.findElement(editIcon).click();

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        usernameInput.clear();
        usernameInput.sendKeys(newUsername);

        driver.findElement(saveButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
    }

    public void deleteUser() {
        System.out.println("🗑️ Deleting user...");
        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(userTableRows));
        deleteBtn.findElement(deleteIcon).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDeleteButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
        System.out.println("✅ User deleted.");
    }
}
