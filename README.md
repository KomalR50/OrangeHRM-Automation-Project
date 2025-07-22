
# OrangeHRM Selenium TestNG Automation Project

## Overview
This project automates key features of the OrangeHRM web application using:
- Selenium WebDriver
- TestNG
- Page Object Model (POM)
- Apache POI (for Excel-based test data)
- ExtentReports
- Java

## Scenarios Covered
### ✅ Scenario 1: Login Module
- Valid Login
- Invalid Login
- Blank Login
- Data-driven testing from Excel
- Screenshot capture on failures
- ExtentReports integration

### ✅ Scenario 2: Admin Module (User Management)
- Search user by username
- Add new user (role, status, employee)
- Edit user (update username)
- Delete user (skipping non-deletable Admin)
- Dynamic locators and waits

## Folder Structure
```
src/
├── main/java/com/orangehrm/pages/        # Page classes
├── main/java/com/orangehrm/utilities/    # Utility classes
└── test/java/com/orangehrm/tests/        # TestNG test classes
    └── base/                              # Base class
testdata/                                  # Excel files
reports/                                   # HTML Extent reports
screenshots/                               # Failure screenshots
```

## Run Tests
1. Set up Maven project in Eclipse/IntelliJ
2. Update ChromeDriver version if needed
3. Run tests from `testng.xml`

## Project Description
See `Project_Description.pdf` inside the root of this project.
