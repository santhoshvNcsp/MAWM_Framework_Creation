package com.p09.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.Status;
import java.util.concurrent.atomic.AtomicInteger;

import java.io.File;

public final class ReportEngine {

    private static ExtentReports extentReports;

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    private static final ThreadLocal<ExtentTest> currentStep =
            new ThreadLocal<>();
    private static ExtentTest executionSummary;

    private static final AtomicInteger totalScenarios = new AtomicInteger();
    private static final AtomicInteger passedScenarios = new AtomicInteger();
    private static final AtomicInteger failedScenarios = new AtomicInteger();


    /*
     * This is the important scenario-level failure flag.
     *
     * Once true, it stays true until the scenario is unloaded.
     * A later PASS must never reset it.
     */
    private static final ThreadLocal<Boolean> scenarioFailed =
            ThreadLocal.withInitial(() -> false);

    /*
     * Used to prevent duplicate "step failed" report entries.
     */
    private static final ThreadLocal<Boolean> failureReported =
            ThreadLocal.withInitial(() -> false);

    /*
     * Stores generated report path.
     */
    private static String reportPath;

    private ReportEngine() {
    }

    public static void initialize() {

        if (extentReports != null) {
            return;
        }

        File reportFolder =
                new File(
                        ReportConstants.REPORT_FOLDER
                );

        if (!reportFolder.exists()) {

            reportFolder.mkdirs();
        }

        reportPath =
                ReportConstants.REPORT_FOLDER
                        + ReportConstants.REPORT_NAME;

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(
                        reportPath
                );

        sparkReporter.config()
                .setDocumentTitle(
                        "MAP Automation Report"
                );

        sparkReporter.config()
                .setReportName(
                        "Automation Execution Report"
                );

        extentReports =
                new ExtentReports();

        extentReports.attachReporter(
                sparkReporter
        );
        executionSummary = extentReports.createTest("EXECUTION SUMMARY");
        totalScenarios.set(0);
        passedScenarios.set(0);
        failedScenarios.set(0);

        updateExecutionSummary();
    }

    public static void createScenario(
            String scenarioName) {

        /*
         * Make sure no state from a previous scenario remains
         * on a reused execution thread.
         */
        scenarioFailed.set(false);
        failureReported.set(false);
        currentStep.remove();

        if (extentReports == null) {
            return;
        }

        extentTest.set(
                extentReports.createTest(
                        scenarioName
                )
        );
    }

    public static void createStep(
            String stepName) {

        ExtentTest scenario =
                extentTest.get();

        if (scenario == null) {
            return;
        }

        currentStep.set(
                scenario.createNode(
                        stepName
                )
        );
    }

    public static ExtentTest getTest() {

        return extentTest.get();
    }

    public static ExtentTest getCurrentStep() {

        ExtentTest step =
                currentStep.get();

        if (step != null) {

            return step;
        }

        /*
         * Fallback to scenario level.
         */
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

    /*
     * IMPORTANT:
     * This method does NOT reset the failure state.
     */
    public static void markScenarioFailure() {

        scenarioFailed.set(true);
    }

    public static boolean isScenarioFailed() {

        return Boolean.TRUE.equals(
                scenarioFailed.get()
        );
    }

    public static void clearScenarioFailure() {

        scenarioFailed.set(false);
    }

    public static void markFailureReported() {

        failureReported.set(true);
    }

    public static boolean isFailureReported() {

        return Boolean.TRUE.equals(
                failureReported.get()
        );
    }

    public static void clearFailureReported() {

        failureReported.set(false);
    }

    public static void markScenarioPassed() {

        ExtentTest scenario =
                extentTest.get();

        if (scenario != null) {

            scenario.pass(
                    "Scenario Passed"
            );
        }
    }

    public static void markScenarioFailed() {

        ExtentTest scenario =
                extentTest.get();

        if (scenario != null) {

            scenario.fail(
                    "Scenario Failed"
            );
        }
    }

    public static String getReportPath() {

        return reportPath;
    }

    public static void unload() {

        /*
         * Clear all scenario-specific ThreadLocal state.
         */
        failureReported.remove();
        scenarioFailed.remove();
        currentStep.remove();
        extentTest.remove();
    }
    public static synchronized void recordScenarioResult(boolean failed) {
        totalScenarios.incrementAndGet();

        if (failed) {
            failedScenarios.incrementAndGet();
        } else {
            passedScenarios.incrementAndGet();
        }

        updateExecutionSummary();
    }

    private static synchronized void updateExecutionSummary() {
        if (executionSummary == null) {
            return;
        }

        int total = totalScenarios.get();
        int passed = passedScenarios.get();
        int failed = failedScenarios.get();

        double passPercentage = total == 0
                ? 0.0
                : (passed * 100.0) / total;

        executionSummary.log(
                Status.INFO,
                "Total Scenarios: " + total
        );

        executionSummary.log(
                Status.INFO,
                "Passed Scenarios: " + passed
        );

        executionSummary.log(
                Status.INFO,
                "Failed Scenarios: " + failed
        );

        executionSummary.log(
                Status.INFO,
                String.format(
                        java.util.Locale.ROOT,
                        "Pass Percentage: %.2f%%",
                        passPercentage
                )
        );
    }
}