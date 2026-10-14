package com.skeleton.utils;

import com.skeleton.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public final class ScreenshotUtil {

    private ScreenshotUtil() {
    }

    /** Base64 string so the image is embedded in the single-file HTML report. */
    public static String captureBase64() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
    }
}