package com.p09.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class ReportEngine {

    private static ExtentReports extentReports;
    private static ExtentSparkReporter sparkReporter;

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    private static final ThreadLocal<ExtentTest> currentStep =
            new ThreadLocal<>();

    private static final AtomicInteger totalScenarios =
            new AtomicInteger();

    private static final AtomicInteger passedScenarios =
            new AtomicInteger();

    private static final AtomicInteger failedScenarios =
            new AtomicInteger();

    private static final AtomicInteger totalSteps =
            new AtomicInteger();

    private static final AtomicInteger passedSteps =
            new AtomicInteger();

    private static final AtomicInteger failedSteps =
            new AtomicInteger();

    private static final AtomicInteger skippedSteps =
            new AtomicInteger();

    // Identity-based tracking prevents duplicate result callbacks from counting
    // the same Extent scenario more than once, including in parallel runs.
    private static final Set<ExtentTest> recordedScenarioTests =
            Collections.newSetFromMap(new IdentityHashMap<>());

    private static final ThreadLocal<Integer> scenarioTotalSteps =
            ThreadLocal.withInitial(() -> 0);

    private static final ThreadLocal<Integer> scenarioPassedSteps =
            ThreadLocal.withInitial(() -> 0);

    private static final ThreadLocal<Integer> scenarioFailedSteps =
            ThreadLocal.withInitial(() -> 0);

    private static final ThreadLocal<Integer> scenarioSkippedSteps =
            ThreadLocal.withInitial(() -> 0);

    private static final ThreadLocal<Boolean> scenarioFailed =
            ThreadLocal.withInitial(() -> false);

    private static final ThreadLocal<Boolean> failureReported =
            ThreadLocal.withInitial(() -> false);

    // Captures the start time independently for each scenario/thread.
    private static final ThreadLocal<Long> scenarioStartTimeMillis =
            new ThreadLocal<>();

    private static String reportPath;

    private ReportEngine() {
    }

    public static synchronized void initialize() {
        if (extentReports != null) {
            return;
        }

        File reportFolder = new File(ReportConstants.REPORT_FOLDER);
        if (!reportFolder.exists() && !reportFolder.mkdirs()) {
            throw new IllegalStateException(
                    "Unable to create report directory: "
                            + reportFolder.getAbsolutePath());
        }

        reportPath = ReportConstants.REPORT_FOLDER
                + ReportConstants.REPORT_NAME;

        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("MAP Automation Report");
        sparkReporter.config().setReportName("Automation Execution Report");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        resetGlobalCounters();
    }

    private static void resetGlobalCounters() {
        totalScenarios.set(0);
        passedScenarios.set(0);
        failedScenarios.set(0);
        totalSteps.set(0);
        passedSteps.set(0);
        failedSteps.set(0);
        skippedSteps.set(0);
        recordedScenarioTests.clear();
    }

    public static void createScenario(String scenarioName) {
        scenarioTotalSteps.set(0);
        scenarioPassedSteps.set(0);
        scenarioFailedSteps.set(0);
        scenarioSkippedSteps.set(0);
        scenarioFailed.set(false);
        failureReported.set(false);
        currentStep.remove();
        scenarioStartTimeMillis.set(System.currentTimeMillis());

        if (extentReports == null) {
            initialize();
        }

        extentTest.set(extentReports.createTest(scenarioName));

        // Count scenarios when their Extent test is created, not when results
        // are recorded. This keeps the total aligned with the report's test list.
        totalScenarios.incrementAndGet();
    }

    public static void createStep(String stepName) {
        ExtentTest scenario = extentTest.get();
        if (scenario == null) {
            return;
        }
        currentStep.set(scenario.createNode(stepName));
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static ExtentTest getCurrentStep() {
        ExtentTest step = currentStep.get();
        return step != null ? step : extentTest.get();
    }

    public static void clearCurrentStep() {
        currentStep.remove();
    }

    public static void markScenarioFailure() {
        scenarioFailed.set(true);
    }

    public static boolean isScenarioFailed() {
        return Boolean.TRUE.equals(scenarioFailed.get());
    }

    public static void clearScenarioFailure() {
        scenarioFailed.set(false);
    }

    public static void markFailureReported() {
        failureReported.set(true);
    }

    public static boolean isFailureReported() {
        return Boolean.TRUE.equals(failureReported.get());
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

    public static String getReportPath() {
        return reportPath;
    }

    public static void unload() {
        failureReported.remove();
        scenarioFailed.remove();
        currentStep.remove();
        extentTest.remove();
        scenarioTotalSteps.remove();
        scenarioPassedSteps.remove();
        scenarioFailedSteps.remove();
        scenarioSkippedSteps.remove();
        scenarioStartTimeMillis.remove();
    }

    public static void recordStepResult(
            io.cucumber.plugin.event.Status status) {

        totalSteps.incrementAndGet();
        scenarioTotalSteps.set(scenarioTotalSteps.get() + 1);

        switch (status) {
            case PASSED -> {
                passedSteps.incrementAndGet();
                scenarioPassedSteps.set(scenarioPassedSteps.get() + 1);
            }
            case FAILED, AMBIGUOUS -> {
                failedSteps.incrementAndGet();
                scenarioFailedSteps.set(scenarioFailedSteps.get() + 1);
                markScenarioFailure();
            }
            case SKIPPED, PENDING, UNDEFINED -> {
                skippedSteps.incrementAndGet();
                scenarioSkippedSteps.set(scenarioSkippedSteps.get() + 1);
                markScenarioFailure();
            }
            default -> markScenarioFailure();
        }
    }

    public static synchronized void recordScenarioResult(boolean failed) {
        // A scenario result should only be applied once, even if multiple
        // framework callbacks attempt to record the same scenario outcome.
        ExtentTest scenario = extentTest.get();
        if (scenario == null || !recordedScenarioTests.add(scenario)) {
            return;
        }

        if (failed) {
            failedScenarios.incrementAndGet();
        } else {
            passedScenarios.incrementAndGet();
        }

        long startTime = scenarioStartTimeMillis.get() == null
                ? System.currentTimeMillis()
                : scenarioStartTimeMillis.get();
        long durationMillis = Math.max(0L, System.currentTimeMillis() - startTime);
        String executionTime = formatDuration(durationMillis);
        addScenarioMetrics(executionTime);
    }

    private static void addScenarioMetrics(String executionTime) {
        ExtentTest scenario = extentTest.get();
        if (scenario == null) {
            return;
        }

        int total = scenarioTotalSteps.get();
        int passed = scenarioPassedSteps.get();
        int failed = scenarioFailedSteps.get();
        int skipped = scenarioSkippedSteps.get();
        double passPercentage = percentage(passed, total);

        scenario.info(
                "<b>Scenario Step Metrics</b>"
                        + "<br>Total Steps: " + total
                        + "<br>Passed Steps: " + passed
                        + "<br>Failed Steps: " + failed
                        + "<br>Skipped Steps: " + skipped
                        + "<br>Step Pass Percentage: "
                        + String.format(Locale.ROOT, "%.2f%%", passPercentage)
                        + "<br>Execution Time: " + executionTime);
    }

    private static String formatDuration(long durationMillis) {
        long totalSeconds = durationMillis / 1_000L;
        long hours = totalSeconds / 3_600L;
        long minutes = (totalSeconds % 3_600L) / 60L;
        long seconds = totalSeconds % 60L;
        return String.format(Locale.ROOT, "%02d:%02d:%02d", hours, minutes, seconds);
    }

    private static double percentage(int passed, int total) {
        return total == 0 ? 0.0 : passed * 100.0 / total;
    }

    public static int getScenarioTotalSteps() {
        return scenarioTotalSteps.get();
    }

    public static int getScenarioPassedSteps() {
        return scenarioPassedSteps.get();
    }

    public static int getScenarioFailedSteps() {
        return scenarioFailedSteps.get();
    }

    public static int getScenarioSkippedSteps() {
        return scenarioSkippedSteps.get();
    }

    public static synchronized void flush() {
        if (extentReports == null) {
            return;
        }

        configureDashboardScript();
        extentReports.flush();
    }

    private static void configureDashboardScript() {
        int scenarios = totalScenarios.get();
        int passed = passedScenarios.get();
        int failed = failedScenarios.get();
        int steps = totalSteps.get();
        int stepsPassed = passedSteps.get();
        int stepsFailed = failedSteps.get();
        int stepsSkipped = skippedSteps.get();

        double scenarioPercentage = percentage(passed, scenarios);
        double stepPercentage = percentage(stepsPassed, steps);

        String dashboardScript = """
                (function () {
                    function addExecutionDashboard() {
                        if (document.getElementById('custom-execution-dashboard')) {
                            return;
                        }

                        var testView = document.querySelector('.test-wrapper.test-view');
                        if (!testView) {
                            return;
                        }

                        var dashboard = document.createElement('section');
                        dashboard.id = 'custom-execution-dashboard';
                        dashboard.style.cssText =
                            'box-sizing:border-box;width:100%%;flex:0 0 100%%;' +
                            'max-width:100%%;order:-1;padding:24px;' +
                            'margin:0 0 20px 0;background:#f4f7fb;' +
                            'border:1px solid #dce3ed;border-radius:10px;' +
                            'font-family:Arial,sans-serif;color:#263445;';

                        dashboard.innerHTML = `
                            <h2 style="margin:0 0 18px 0;font-size:22px;">
                                Automation Execution Dashboard
                            </h2>
                            <div class="metric-grid">
                                <div class="metric metric-primary">
                                    <div class="metric-label">Total Scenarios</div>
                                    <div class="metric-value">%d</div>
                                </div>
                                <div class="metric metric-success">
                                    <div class="metric-label">Passed Scenarios</div>
                                    <div class="metric-value">%d</div>
                                </div>
                                <div class="metric metric-danger">
                                    <div class="metric-label">Failed Scenarios</div>
                                    <div class="metric-value">%d</div>
                                </div>
                                <div class="metric metric-purple">
                                    <div class="metric-label">Scenario Pass Rate</div>
                                    <div class="metric-value">%.2f%%</div>
                                </div>
                                <div class="metric metric-cyan">
                                    <div class="metric-label">Total Steps</div>
                                    <div class="metric-value">%d</div>
                                </div>
                                <div class="metric metric-success">
                                    <div class="metric-label">Passed Steps</div>
                                    <div class="metric-value">%d</div>
                                </div>
                                <div class="metric metric-danger">
                                    <div class="metric-label">Failed Steps</div>
                                    <div class="metric-value">%d</div>
                                </div>
                                <div class="metric metric-warning">
                                    <div class="metric-label">Skipped Steps</div>
                                    <div class="metric-value">%d</div>
                                </div>
                                <div class="metric metric-purple">
                                    <div class="metric-label">Step Pass Rate</div>
                                    <div class="metric-value">%.2f%%</div>
                                </div>
                            </div>`;

                        // Convert the scenario-level metrics log entry into a
                        // compact summary panel above the Status/Timestamp/Details table.
                        function addScenarioMetricPanels() {
                            document.querySelectorAll('.test-item .test-contents').forEach(function(contents) {
                                var detailBody = contents.querySelector('.detail-body');
                                if (!detailBody || detailBody.querySelector('.custom-scenario-metrics')) {
                                    return;
                                }

                                var metricRow = Array.from(detailBody.querySelectorAll('tr.event-row'))
                                    .find(function(row) {
                                        return /Scenario Step Metrics/i.test(row.textContent || '');
                                    });

                                if (!metricRow) {
                                    return;
                                }

                                var metricsText = metricRow.textContent || '';
                                function readMetric(pattern, fallback) {
                                    var match = metricsText.match(pattern);
                                    return match ? match[1] : fallback;
                                }

                                var total = readMetric(/Total Steps:\\s*(\\d+)/i, '0');
                                var passed = readMetric(/Passed Steps:\\s*(\\d+)/i, '0');
                                var failed = readMetric(/Failed Steps:\\s*(\\d+)/i, '0');
                                var passRate = readMetric(/Step Pass Percentage:\\s*([\\d.]+)/i, '0.00');
                                var executionTime = readMetric(/Execution Time:\\s*(\\d{2,}:\\d{2}:\\d{2})/i, '00:00:00');
                                if (passRate.indexOf('%%') === -1) {
                                    passRate += '%%';
                                }

                                var panel = document.createElement('section');
                                panel.className = 'custom-scenario-metrics';
                                panel.innerHTML = `
                                    <h6 class="scenario-metrics-title">Scenario Step Summary</h6>
                                    <div class="scenario-metrics-grid">
                                        <div class="scenario-metric metric-primary">
                                            <div class="scenario-metric-label">Total Steps</div>
                                            <div class="scenario-metric-value">${total}</div>
                                        </div>
                                        <div class="scenario-metric metric-success">
                                            <div class="scenario-metric-label">Steps Passed</div>
                                            <div class="scenario-metric-value">${passed}</div>
                                        </div>
                                        <div class="scenario-metric metric-danger">
                                            <div class="scenario-metric-label">Steps Failed</div>
                                            <div class="scenario-metric-value">${failed}</div>
                                        </div>
                                        <div class="scenario-metric metric-purple">
                                            <div class="scenario-metric-label">Pass Percentage</div>
                                            <div class="scenario-metric-value">${passRate}</div>
                                        </div>
                                        <div class="scenario-metric metric-info">
                                            <div class="scenario-metric-label">Execution Time</div>
                                            <div class="scenario-metric-value">${executionTime}</div>
                                        </div>
                                    </div>`;

                                var firstTable = detailBody.querySelector('table');
                                if (firstTable) {
                                    detailBody.insertBefore(panel, firstTable);
                                } else {
                                    detailBody.insertBefore(panel, detailBody.firstChild);
                                }
                                // The same figures now appear in the summary panel,
                                // so remove the old info row to avoid duplicate metrics.
                                metricRow.remove();
                            });
                        }

                        var style = document.createElement('style');
                        style.textContent = `
                            .test-wrapper.test-view {
                                display:flex;
                                flex-wrap:wrap !important;
                            }
                            .custom-scenario-metrics {
                                box-sizing:border-box;
                                margin:0 0 18px 0;
                                padding:16px;
                                border:1px solid #dce3ed;
                                border-radius:8px;
                                background:#f8fafc;
                            }
                            .scenario-metrics-title {
                                margin:0 0 12px 0;
                                color:#334155;
                                font-weight:600;
                            }
                            .scenario-metrics-grid {
                                display:grid;
                                grid-template-columns:repeat(5,minmax(100px,1fr));
                                gap:10px;
                            }
                            .scenario-metric {
                                box-sizing:border-box;
                                padding:12px;
                                background:#ffffff;
                                border:1px solid #e0e6ef;
                                border-radius:8px;
                                transition:background-color 0.2s ease, border-color 0.2s ease;
                            }
                            .metric-primary {
                                background:#eaf2ff !important;
                                border-color:#c8dcff !important;
                            }
                            .metric-primary .scenario-metric-value,
                            #custom-execution-dashboard .metric-primary .metric-value {
                                color:#244c87;
                            }
                            .metric-purple {
                                background:#f0ebff !important;
                                border-color:#d9ccff !important;
                            }
                            .metric-purple .scenario-metric-value,
                            #custom-execution-dashboard .metric-purple .metric-value {
                                color:#583797;
                            }
                            .metric-cyan {
                                background:#e5f7fa !important;
                                border-color:#bcecf3 !important;
                            }
                            .metric-cyan .scenario-metric-value,
                            #custom-execution-dashboard .metric-cyan .metric-value {
                                color:#12677a;
                            }
                            .metric-teal {
                                background:#e4f6ef !important;
                                border-color:#bfe8d8 !important;
                            }
                            .metric-teal .scenario-metric-value,
                            #custom-execution-dashboard .metric-teal .metric-value {
                                color:#206b4f;
                            }
                            .metric-success {
                                background:#eaf7ee !important;
                                border-color:#b9e3c5 !important;
                            }
                            .metric-success .scenario-metric-value,
                            #custom-execution-dashboard .metric-success .metric-value {
                                color:#197344;
                            }
                            .metric-danger {
                                background:#fff0f0 !important;
                                border-color:#f2c1c1 !important;
                            }
                            .metric-danger .scenario-metric-value,
                            #custom-execution-dashboard .metric-danger .metric-value {
                                color:#c62828;
                            }
                            .metric-warning {
                                background:#fff7e6 !important;
                                border-color:#f3d79b !important;
                            }
                            #custom-execution-dashboard .metric-warning .metric-value {
                                color:#9a6700;
                            }
                            .metric-info {
                                background:#edf5ff !important;
                                border-color:#c5dcfa !important;
                            }
                            .metric-info .scenario-metric-value {
                                color:#245ea8;
                            }
                            .scenario-metric-label {
                                margin-bottom:8px;
                                color:#65748b;
                                font-size:12px;
                            }
                            .scenario-metric-value {
                                color:#263445;
                                font-size:21px;
                                font-weight:700;
                            }
                            @media (max-width: 700px) {
                                .scenario-metrics-grid {
                                    grid-template-columns:repeat(2,minmax(100px,1fr));
                                }
                            }
                            #custom-execution-dashboard .metric-grid {
                                display:grid;
                                grid-template-columns:repeat(auto-fit,minmax(150px,1fr));
                                gap:14px;
                            }
                            #custom-execution-dashboard .metric {
                                box-sizing:border-box;
                                background:#ffffff;
                                border:1px solid #e0e6ef;
                                border-radius:8px;
                                padding:16px;
                                min-height:80px;
                            }
                            #custom-execution-dashboard .metric-label {
                                color:#65748b;
                                font-size:13px;
                                margin-bottom:10px;
                            }
                            #custom-execution-dashboard .metric-value {
                                font-size:25px;
                                font-weight:700;
                            }
                            #custom-execution-dashboard .metric-success .metric-label {
                                color:#276749;
                            }
                            #custom-execution-dashboard .metric-danger .metric-label {
                                color:#a61b1b;
                            }
                            #custom-execution-dashboard .metric-warning .metric-label {
                                color:#8a5a00;
                            }
                            #custom-execution-dashboard .metric-primary .metric-label {
                                color:#315a96;
                            }
                            #custom-execution-dashboard .metric-purple .metric-label {
                                color:#6945a6;
                            }
                            #custom-execution-dashboard .metric-cyan .metric-label {
                                color:#18758a;
                            }
                            #custom-execution-dashboard .metric-teal .metric-label {
                                color:#267a5b;
                            }
                        `;
                        document.head.appendChild(style);

                        // Put the dashboard inside the test view so it disappears
                        // when the user switches to ExtentReports' own dashboard.
                        // It occupies its own row; the scenario list and detail pane
                        // retain their regular side-by-side layout underneath.
                        testView.insertBefore(dashboard, testView.firstChild);
                        addScenarioMetricPanels();
                    }

                    if (document.readyState === 'loading') {
                        document.addEventListener('DOMContentLoaded', addExecutionDashboard);
                    } else {
                        addExecutionDashboard();
                    }
                })();
                """.formatted(
                scenarios,
                passed,
                failed,
                scenarioPercentage,
                steps,
                stepsPassed,
                stepsFailed,
                stepsSkipped,
                stepPercentage);

        sparkReporter.config().setJs(dashboardScript);
    }
}

