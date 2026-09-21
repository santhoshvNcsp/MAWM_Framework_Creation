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

        log(StepStatus.PASS, message);

    }

//    public void fail(String message) {
//
//        try {
//
//            String screenshotPath =
//                    ScreenshotUtil.capture(
//                            message.replaceAll(
//                                    "[^a-zA-Z0-9_-]",
//                                    "_"));
//
//            ReportEngine.getCurrentStep()
//                    .fail(
//                            message,
//                            MediaEntityBuilder
//                                    .createScreenCaptureFromPath(
//                                            screenshotPath)
//                                    .build());
//
//            ReportEngine.markFailureReported();
//
//        } catch (Exception e) {
//
//            log(StepStatus.FAIL, message);
//
//        }
//        throw new RuntimeException(message);
//
//    }
public void fail(String message) {

    try {

        String screenshotPath =
                ScreenshotUtil.capture(
                        message.replaceAll("[^a-zA-Z0-9_-]", "_"));

        ReportEngine.getCurrentStep()
                .fail(
                        message,
                        MediaEntityBuilder
                                .createScreenCaptureFromPath(screenshotPath)
                                .build()
                );

        ReportEngine.markFailureReported();
        ReportEngine.markScenarioFailure();
//        ReportEngine.markStepFailed();

    } catch (Exception e) {

        log(StepStatus.FAIL, message);

        ReportEngine.markScenarioFailure();
//        ReportEngine.markStepFailed();
    }

    if (!ConfigManager.getBoolean("continue.on.failure")) {
        throw new RuntimeException(message);
    }
}
    public void failWithScreenshot(
            String message,
            String screenshotPath) {

        try {

            ReportEngine.getCurrentStep()
                    .fail(
                            message,
                            MediaEntityBuilder
                                    .createScreenCaptureFromPath(
                                            screenshotPath)
                                    .build());

        } catch (Exception e) {

            // If screenshot attachment fails,
            // still report the actual failure.
            log(StepStatus.FAIL, message);

        }

    }

    public void info(String message) {

        log(StepStatus.INFO, message);

    }

    public void warning(String message) {

        log(StepStatus.WARNING, message);

    }

    public void attachScreenshot(String screenshotPath) {

        try {

            ReportEngine.getCurrentStep().info(
                    MediaEntityBuilder
                            .createScreenCaptureFromPath(
                                    screenshotPath)
                            .build());

        } catch (Exception ignored) {

        }

    }

    public void flush() {

        ReportEngine.flush();

    }

    private void log(
            StepStatus status,
            String message) {

        switch (status) {

            case PASS ->
                    ReportEngine.getCurrentStep()
                            .pass(message);

            case FAIL ->
                    ReportEngine.getCurrentStep()
                            .fail(message);

            case INFO ->
                    ReportEngine.getCurrentStep()
                            .info(message);

            case WARNING ->
                    ReportEngine.getCurrentStep()
                            .warning(message);

            case SKIP ->
                    ReportEngine.getCurrentStep()
                            .skip(message);

        }

    }
    public void addReportStepWithScreenshot(
            StepStatus status,
            String message) {

        try {

            String screenshotPath =
                    ScreenshotUtil.capture(
                            message.replaceAll(
                                    "[^a-zA-Z0-9_-]",
                                    "_"));

            ReportEngine.getCurrentStep()
                    .log(
                            statusToExtentStatus(status),
                            message,
                            MediaEntityBuilder
                                    .createScreenCaptureFromPath(
                                            screenshotPath)
                                    .build());

        } catch (Exception e) {

            log(status, message);

            warning(
                    "Unable to capture screenshot: "
                            + e.getMessage());

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

        log(status, message);

    }


    public String getReportPath() {

        return ReportEngine.getReportPath();

    }

}