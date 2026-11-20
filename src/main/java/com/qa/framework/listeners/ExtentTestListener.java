package com.qa.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.framework.driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/** Writes target/extent-report/index.html, logs each test, and attaches a screenshot to UI failures. */
public class ExtentTestListener implements ITestListener {

    private static final Logger LOG = LogManager.getLogger(ExtentTestListener.class);
    private static final ExtentReports REPORT = new ExtentReports();
    private static final ThreadLocal<ExtentTest> CURRENT = new ThreadLocal<>();

    static {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report/index.html");
        spark.config().setReportName("UI & API Test Automation Report");
        spark.config().setDocumentTitle("Test Report");
        REPORT.attachReporter(spark);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String description = result.getMethod().getDescription();
        String name = description == null || description.isEmpty() ? result.getMethod().getMethodName() : description;
        CURRENT.set(REPORT.createTest(name));
        LOG.info("START {}", name);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        CURRENT.get().pass("Passed");
        LOG.info("PASS  {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("FAIL  {}", result.getMethod().getMethodName(), result.getThrowable());
        ExtentTest test = CURRENT.get();
        WebDriver driver = DriverFactory.getDriver();
        if (driver instanceof TakesScreenshot shot) {
            String base64 = shot.getScreenshotAs(OutputType.BASE64);
            test.fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
        } else {
            test.fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        CURRENT.get().skip("Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        REPORT.flush();
    }
}
