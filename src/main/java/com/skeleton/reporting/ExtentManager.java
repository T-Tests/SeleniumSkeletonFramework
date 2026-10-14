package com.skeleton.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.skeleton.config.ConfigManager;

/** Creates the single ExtentReports instance for the run (lazy, thread-safe). */
public final class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getExtent() {
        if (extent == null) {
            ConfigManager config = ConfigManager.getInstance();
            ExtentSparkReporter spark = new ExtentSparkReporter(config.get("report.dir") + "/ExtentReport.html");
            spark.config().setReportName("Selenium Skeleton Framework");
            spark.config().setDocumentTitle("Test Execution Report");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Browser", config.get("browser"));
            extent.setSystemInfo("Base URL", config.baseUrl());
        }
        return extent;
    }
}