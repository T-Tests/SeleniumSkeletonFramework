# Selenium Skeleton Framework

Java 17 + Selenium 4 + TestNG + ExtentReports + Apache POI (.xlsx) + Maven.
Sample app under test: https://www.saucedemo.com

## Run
```
mvn test                                  # default: chrome, headed
mvn test -Dheadless=true -Dbrowser=edge   # any config.properties key can be overridden with -D
```
Report: `target/extent-report/ExtentReport.html` (failures include an embedded screenshot).

## Layout
```
src/main/java/com/skeleton
  config/      ConfigManager       Singleton reading config.properties (-D overrides)
  enums/       BrowserType         Enum; each browser overrides createDriver()
  driver/      DriverManager       ThreadLocal driver factory (parallel safe)
  core/        Loadable, BasePage, BaseComponent   interface + abstract classes (the POM engine)
  components/  HeaderComponent     Reusable UI part, used by pages via composition
  pages/       LoginPage, InventoryPage, CartPage  Locators + actions only, no assertions
  models/      User                Immutable data object
  utils/       TestDataReader, ExcelReader, ScreenshotUtil
  reporting/   ExtentManager, TestListener         Report setup + TestNG listener
  exceptions/  FrameworkException
src/test/java/com/skeleton
  base/BaseTest, dataproviders/TestDataProviders, tests/LoginTest, CartTest
src/test/resources/testdata/TestData.xlsx          One sheet per scenario group; row 1 = headers
```

## OOP concepts and where to see them
| Concept | Example |
|---|---|
| Abstraction | `BasePage`, `BaseComponent` (abstract), `Loadable`, `TestDataReader` (interfaces) |
| Inheritance | `LoginPage extends BasePage`, `LoginTest extends BaseTest`, `HeaderComponent extends BaseComponent` |
| Encapsulation | Private locators in pages, `User` (final fields + getters), private `ConfigManager` state |
| Polymorphism | Overriding `isLoaded()` per page and `BrowserType.createDriver()` per constant; overloading `click(By/WebElement)`, `addToCart(String/String...)`, `ExcelReader` constructors |
| Composition | Pages own a `HeaderComponent` |
| Singleton / Factory / Observer | `ConfigManager` / `DriverManager` + `BrowserType` / `TestListener` |
| Enums, generics, exceptions, inner-constant bodies | `BrowserType`, `waitUntilLoaded()`, `FrameworkException` |

## Enhanced POM rules
1. Tests talk to page methods only - never `By` or `WebElement`.
2. Page actions return the next page (`loginAs(...)` returns `InventoryPage`).
3. Assertions live in tests, not pages.
4. Shared UI parts are components, not copy-pasted into pages.
5. Every page defines `isLoaded()`; navigation waits on it.

## Add a new test in 4 steps
1. Create `XxxPage extends BasePage` (locators, actions, `isLoaded()`, `getPageName()`).
2. Return it from the page that navigates to it.
3. Add a sheet to `TestData.xlsx` and a `@DataProvider` in `TestDataProviders` if data-driven.
4. Write a `@Test` in a class extending `BaseTest`; it is picked up by `testng.xml` (package `com.skeleton.tests`).

## Notes
- Drivers are resolved automatically by Selenium Manager (no driver binaries needed).
- Chrome versions newer than Selenium may log a harmless "CDP version" warning; bump `selenium.version` in `pom.xml` to silence it.
- Parallel run: set `parallel="methods" thread-count="3"` on `<suite>` in `testng.xml`.
