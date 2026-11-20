# UI & API Test Automation Framework

## Project Summary
A hybrid framework for UI and API automation using Selenium with Java. It follows the Page Object Model (POM) and includes Extent reports with failure screenshots, Log4j2 logging, and data-driven tests fed from JSON and Excel files.

## Technology Used
- Language: Java 17+
- Build system: Maven
- Automation tools: Selenium 4, TestNG, Rest Assured
- Data: JSON (json-simple) and MS Excel (Apache POI)
- Reporting: Extent Reports (Spark) with screenshots
- Logging: Log4j2

## What Is Tested
UI (https://www.saucedemo.com):
- Login with a valid user and check the Products page
- Login with a wrong password and check the error message
- Add an item to the cart and check the cart badge
- Data-driven login scenarios read from `testdata/users.json`

API (https://jsonplaceholder.typicode.com):
- Get the post list, get a single post, filter by user
- Create a post and check the response body
- Request a missing post and expect 404

## Project Structure
```
src/main/java/com/qa/framework
  config/      ConfigReader        config.properties with -D overrides
  driver/      DriverFactory       ThreadLocal WebDriver (chrome, firefox, edge)
  pages/       BasePage, LoginPage, InventoryPage
  listeners/   ExtentTestListener  report, logging and screenshot on failure
  utils/       JsonUtils, ExcelUtils
src/test/java/com/qa/framework
  base/        BaseUiTest
  ui/          LoginTest, DataDrivenLoginTest
  api/         PostsApiTest
src/test/resources
  config.properties, log4j2.xml, testdata/
UITestSuite.xml   APITestSuite.xml   testng.xml (both)
```

## How to Run
1. Clone the project.
2. Open it in IntelliJ: File > Open > select the folder > Open as project.
3. Run from the terminal:

```bash
mvn clean test -DsuiteFile=UITestSuite.xml     # UI suite
mvn clean test -DsuiteFile=APITestSuite.xml    # API suite
mvn clean test                                 # both
```

Other options: `-Dbrowser=firefox -Dheadless=false`.

## Output
- Extent report: `target/extent-report/index.html`
- Log file: `target/logs/test.log`
- TestNG results: `target/surefire-reports`

## CI
`.github/workflows/tests.yml` runs the tests on every push and uploads the reports.