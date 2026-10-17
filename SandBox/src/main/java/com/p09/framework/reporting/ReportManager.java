package com.p09.framework.reporting;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.p09.framework.config.ConfigManager;
import com.p09.framework.utilities.ScreenshotUtil;

public final class ReportManager {

    private static final ReportManager INSTANCE =
            new ReportManager();

    private ReportManager() {
    }

    public static ReportManager getInstance() {
        return INSTANCE;
    }

    public void initialize() {

        ReportEngine.initialize();
    }

    public void createScenario(String scenarioName) {

        ReportEngine.createScenario(scenarioName);
    }

    public void createStep(String stepName) {

        ReportEngine.createStep(stepName);
    }

    public void pass(String message) {

        log(
                StepStatus.PASS,
                message
        );
    }

    public void fail(String message) {

        try {

            String screenshotPath =
                    ScreenshotUtil.capture(
                            message.replaceAll(
                                    "[^a-zA-Z0-9_-]",
                                    "_"
                            )
                    );

            if (ReportEngine.getCurrentStep() != null) {

                ReportEngine.getCurrentStep()
                        .fail(
                                message,
                                MediaEntityBuilder
                                        .createScreenCaptureFromPath(
                                                screenshotPath
                                        )
                                        .build()
                        );

            } else {

                log(
                        StepStatus.FAIL,
                        message
                );
            }

            // IMPORTANT:
            // Report failure must make the whole scenario fail.
            ReportEngine.markFailureReported();
            ReportEngine.markScenarioFailure();

        } catch (Exception e) {

            // Even if screenshot/report attachment fails,
            // the actual operation must remain a failure.
            log(
                    StepStatus.FAIL,
                    message
            );

            ReportEngine.markScenarioFailure();
        }

        // Existing framework behavior preserved.
        //
        // false = stop immediately
        // true  = continue execution, but final scenario
        //         will still be FAILED because scenarioFailed
        //         is tracked in ReportEngine.
        if (!ConfigManager.getBoolean("continue.on.failure")) {

            throw new RuntimeException(message);
        }
    }

    public void failWithScreenshot(
            String message,
            String screenshotPath) {

        // Mark the scenario failed FIRST.
        // This ensures that even if screenshot attachment fails,
        // the scenario is still considered failed.
        ReportEngine.markScenarioFailure();

        try {

            if (ReportEngine.getCurrentStep() != null) {

                ReportEngine.getCurrentStep()
                        .fail(
                                message,
                                MediaEntityBuilder
                                        .createScreenCaptureFromPath(
                                                screenshotPath
                                        )
                                        .build()
                        );

            } else {

                log(
                        StepStatus.FAIL,
                        message
                );
            }

            ReportEngine.markFailureReported();

        } catch (Exception e) {

            log(
                    StepStatus.FAIL,
                    message
            );
        }
    }

    public void info(String message) {

        log(
                StepStatus.INFO,
                message
        );
    }

    public void warning(String message) {

        log(
                StepStatus.WARNING,
                message
        );
    }

    public void attachScreenshot(String screenshotPath) {

        try {

            if (ReportEngine.getCurrentStep() != null) {

                ReportEngine.getCurrentStep()
                        .info(
                                MediaEntityBuilder
                                        .createScreenCaptureFromPath(
                                                screenshotPath
                                        )
                                        .build()
                        );
            }

        } catch (Exception ignored) {

            // Screenshot attachment must not break
            // the actual automation flow.
        }
    }

    public void flush() {

        ReportEngine.flush();
    }

    private void log(
            StepStatus status,
            String message) {

        if (ReportEngine.getCurrentStep() == null) {
            return;
        }

        switch (status) {

            case PASS -> {

                ReportEngine.getCurrentStep()
                        .pass(message);
            }

            case FAIL -> {

                // IMPORTANT:
                // A report FAIL is a scenario FAIL.
                ReportEngine.markScenarioFailure();

                ReportEngine.getCurrentStep()
                        .fail(message);
            }

            case INFO -> {

                ReportEngine.getCurrentStep()
                        .info(message);
            }

            case WARNING -> {

                ReportEngine.getCurrentStep()
                        .warning(message);
            }

            case SKIP -> {

                ReportEngine.markScenarioFailure();

                ReportEngine.getCurrentStep()
                        .skip(message);
            }
        }
    }

    public void addReportStepWithScreenshot(
            StepStatus status,
            String message) {

        // IMPORTANT:
        // Mark the scenario failed BEFORE trying screenshot capture.
        // Otherwise a screenshot-related exception could hide the
        // original validation failure.
        if (status == StepStatus.FAIL) {

            ReportEngine.markScenarioFailure();
        }

        try {

            String screenshotPath =
                    ScreenshotUtil.capture(
                            message.replaceAll(
                                    "[^a-zA-Z0-9_-]",
                                    "_"
                            )
                    );

            if (ReportEngine.getCurrentStep() != null) {

                ReportEngine.getCurrentStep()
                        .log(
                                statusToExtentStatus(status),
                                message,
                                MediaEntityBuilder
                                        .createScreenCaptureFromPath(
                                                screenshotPath
                                        )
                                        .build()
                        );
            }

            if (status == StepStatus.FAIL) {

                ReportEngine.markFailureReported();
            }

        } catch (Exception e) {

            // Screenshot failed, but the requested report status
            // still needs to be recorded.
            log(
                    status,
                    message
            );

            if (status == StepStatus.FAIL) {

                ReportEngine.markScenarioFailure();
                ReportEngine.markFailureReported();
            }

            try {

                warning(
                        "Unable to capture screenshot: "
                                + e.getMessage()
                );

            } catch (Exception ignored) {
                // Never mask the actual automation/report failure.
            }
        }
    }

    private com.aventstack.extentreports.Status
    statusToExtentStatus(StepStatus status) {

        return switch (status) {

            case PASS ->
                    com.aventstack.extentreports.Status.PASS;

            case FAIL ->
                    com.aventstack.extentreports.Status.FAIL;

            case INFO ->
                    com.aventstack.extentreports.Status.INFO;

            case WARNING ->
                    com.aventstack.extentreports.Status.WARNING;

            case SKIP ->
                    com.aventstack.extentreports.Status.SKIP;
        };
    }

    public void addReportStepWithoutScreenshot(
            StepStatus status,
            String message) {

        log(
                status,
                message
        );
    }

    public String getReportPath() {

        return ReportEngine.getReportPath();
    }
}