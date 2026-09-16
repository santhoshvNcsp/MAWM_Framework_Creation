package com.p09.framework.execution;

import com.p09.framework.config.ConfigManager;
import com.p09.framework.driver.BrowserContext;
import com.p09.framework.notification.EmailManager;
import com.p09.framework.reporting.ReportManager;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutionLauncher {

    private static void openReport(ReportManager report) {

        try {

            String reportPath = report.getReportPath();

            if (reportPath == null) {

                System.out.println(
                        "Report path is not available.");

                return;
            }

            File reportFile = new File(reportPath);

            if (!reportFile.exists()) {

                System.out.println(
                        "Report file not found: "
                                + reportFile.getAbsolutePath());

                return;
            }

            if (Desktop.isDesktopSupported()) {

                Desktop.getDesktop()
                        .browse(reportFile.toURI());

                System.out.println(
                        "Report opened: "
                                + reportFile.getAbsolutePath());

            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to open report automatically: "
                            + e.getMessage());

        }

    }

    public static void main(String[] args) {

        ReportManager report =
                ReportManager.getInstance();

        ExecutionSummary summary =
                new ExecutionSummary();

        try {

            report.initialize();

            // Temporary verification
            System.out.println(
                    "Feature Path : "
                            + ConfigManager.get("feature.path"));

            System.out.println(
                    "Glue Package : "
                            + ConfigManager.get("glue.package"));

            RunManager runManager =
                    new RunManager();

            CucumberExecutor executor =
                    new CucumberExecutor();

            List<RunDetails> scenarios =
                    runManager.getExecutableScenarios();

            /*
             * Set the total number of test cases
             * before execution starts.
             */
            summary.setTotalTestCases(
                    scenarios.size());

            System.out.println(
                    "Total executable scenarios : "
                            + scenarios.size());

            String executionMode =
                    ConfigManager.get("execution.mode");

            if ("PARALLEL".equalsIgnoreCase(
                    executionMode)) {

                ExecutorService executorService =
                        Executors.newFixedThreadPool(
                                ConfigManager.getInt(
                                        "parallel.threads"));

                for (RunDetails run : scenarios) {

                    executorService.submit(() -> {

                        System.out.println(
                                "Feature : "
                                        + run.getFeatureFile());

                        System.out.println(
                                "Scenario : "
                                        + run.getScenarioName());

                        BrowserContext.setBrowser(
                                run.getBrowser());

                        boolean passed =
                                executor.execute(run);

                        if (passed) {

                            summary.incrementPassed();

                        } else {

                            summary.incrementFailed();

                        }

                        /*
                         * Display execution progress
                         * after every completed test case.
                         */
                        summary.printExecutionProgress();

                    });

                }

                executorService.shutdown();

                try {

                    executorService.awaitTermination(
                            Long.MAX_VALUE,
                            TimeUnit.MINUTES);

                } catch (InterruptedException e) {

                    Thread.currentThread()
                            .interrupt();

                    throw new RuntimeException(
                            "Execution interrupted",
                            e);

                }

            } else {

                for (RunDetails run : scenarios) {

                    System.out.println(
                            "Feature : "
                                    + run.getFeatureFile());

                    System.out.println(
                            "Scenario : "
                                    + run.getScenarioName());

                    BrowserContext.setBrowser(
                            run.getBrowser());

                    boolean passed =
                            executor.execute(run);

                    if (passed) {

                        summary.incrementPassed();

                    } else {

                        summary.incrementFailed();

                    }

                    /*
                     * Display execution progress
                     * after every completed test case.
                     */
                    summary.printExecutionProgress();

                }

            }

        } finally {

            report.flush();

            summary.printSummary();

            try {

                EmailManager.sendReport();

            } catch (Exception e) {

                System.out.println(
                        "Unable to send report email: "
                                + e.getMessage());

            }

            openReport(report);

        }

    }

}