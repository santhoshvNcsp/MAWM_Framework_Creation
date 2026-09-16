package com.p09.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public final class ReportEngine {

    private static ExtentReports extentReports;

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    private static final ThreadLocal<ExtentTest> currentStep =
            new ThreadLocal<>();

    // Stores the generated report path
    private static String reportPath;

    private ReportEngine() {
    }

    public static void initialize() {

        if (extentReports != null) {
            return;
        }

        File reportFolder =
                new File(ReportConstants.REPORT_FOLDER);

        if (!reportFolder.exists()) {
            reportFolder.mkdirs();
        }

        reportPath =
                ReportConstants.REPORT_FOLDER +
                        ReportConstants.REPORT_NAME;

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(reportPath);

        sparkReporter.config()
                .setDocumentTitle("MAP Automation Report");

        sparkReporter.config()
                .setReportName("Automation Execution Report");

        extentReports = new ExtentReports();

        extentReports.attachReporter(sparkReporter);
    }

    public static void createScenario(String scenarioName) {

        extentTest.set(
                extentReports.createTest(scenarioName));

    }

    public static void createStep(String stepName) {

        ExtentTest scenario = extentTest.get();

        if (scenario == null) {
            return;
        }

        currentStep.set(
                scenario.createNode(stepName));

    }

    public static ExtentTest getTest() {

        return extentTest.get();

    }

    public static ExtentTest getCurrentStep() {

        ExtentTest step = currentStep.get();

        if (step != null) {
            return step;
        }

        return extentTest.get();

    }

    public static void clearCurrentStep() {

        currentStep.remove();

    }

    public static void flush() {

        if (extentReports != null) {
            extentReports.flush();
        }

    }

    public static void unload() {

        failureReported.remove();

        currentStep.remove();

        extentTest.remove();

    }

    public static String getReportPath() {

        return reportPath;

    }
    private static final ThreadLocal<Boolean> failureReported =
            ThreadLocal.withInitial(() -> false);
    public static void markFailureReported() {

        failureReported.set(true);

    }

    public static boolean isFailureReported() {

        return failureReported.get();

    }

    public static void clearFailureReported() {

        failureReported.set(false);

    }
    public static void markScenarioPassed() {

        ExtentTest scenario = extentTest.get();

        if (scenario != null) {
            scenario.pass("Scenario Passed");
        }

    }

    public static void markScenarioFailed() {

        ExtentTest scenario = extentTest.get();

        if (scenario != null) {
            scenario.fail("Scenario Failed");
        }

    }


}