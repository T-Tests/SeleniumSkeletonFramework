package com.skeleton.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.skeleton.driver.DriverManager;
import com.skeleton.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * OBSERVER: TestNG calls these methods on test events; we turn them into report entries.
 * ThreadLocal keeps each parallel test's report node separate.
 */
public class TestListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> CURRENT = new ThreadLocal<>();

    /** Lets tests write steps to the report: TestListener.log("Opened login page"). */
    public static void log(String message) {
        ExtentTest test = CURRENT.get();
        if (test != null) {
            test.info(message);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String description = result.getMethod().getDescription();
        String name = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        if (params.length > 0) {
            name += " " + params[0]; // first DataProvider value identifies the data row
        }
        ExtentTest test = ExtentManager.getExtent().createTest(name, description);
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        CURRENT.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        CURRENT.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = CURRENT.get();
        test.fail(result.getThrowable());
        try {
            DriverManager.getDriver();
            test.fail("Screenshot at failure",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtil.captureBase64()).build());
        } catch (RuntimeException ignored) {
            test.warning("No screenshot: browser not available");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = CURRENT.get();
        if (test == null) {
            test = ExtentManager.getExtent().createTest(result.getMethod().getMethodName());
        }
        test.skip(result.getThrowable() != null ? result.getThrowable().getMessage() : "Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getExtent().flush();
    }
}