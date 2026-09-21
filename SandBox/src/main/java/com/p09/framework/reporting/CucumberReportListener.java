package com.p09.framework.reporting;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.p09.framework.utilities.ScreenshotUtil;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import io.cucumber.plugin.event.TestCaseFinished;

public class CucumberReportListener
        implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(
                TestStepStarted.class,
                this::handleStepStarted);

        publisher.registerHandlerFor(
                TestStepFinished.class,
                this::handleStepFinished);

        publisher.registerHandlerFor(
                TestCaseFinished.class,
                this::handleTestCaseFinished);

    }

    private void handleStepStarted(
            TestStepStarted event) {

        if (event.getTestStep()
                instanceof PickleStepTestStep pickleStep) {

            String stepText =
                    pickleStep.getStep().getKeyword()
                            + " "
                            + pickleStep.getStep().getText();

            System.out.println(
                    "Cucumber Step : " + stepText);

            ReportEngine.createStep(stepText);

            // Reset failure flag for this step
            ReportEngine.clearFailureReported();

        }

    }

    private void handleStepFinished(TestStepFinished event) {

        if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            return;
        }

        io.cucumber.plugin.event.Status status =
                event.getResult().getStatus();

        System.out.println(
                "STEP STATUS: "
                        + status
        );

        switch (status) {

            case PASSED -> {
                // Step executed successfully.
                // No need to explicitly log PASS because the Extent node
                // will remain successful.
            }

//            case FAILED -> {
//
//                if (ReportEngine.isFailureReported()) {
//                    ReportEngine.clearFailureReported();
//                    return;
//                }
//
//                try {
//
//                    String screenshotPath =
//                            ScreenshotUtil.capture("Failed_Step");
//
//                    ReportEngine.getCurrentStep().fail(
//                            "Step Failed",
//                            com.aventstack.extentreports.MediaEntityBuilder
//                                    .createScreenCaptureFromPath(screenshotPath)
//                                    .build()
//                    );
//
//                } catch (Exception e) {
//
//                    ReportEngine.getCurrentStep()
//                            .fail(
//                                    "Step Failed - Screenshot unavailable: "
//                                            + e.getMessage()
//                            );
//                }
//            }
            case FAILED -> {

                ReportEngine.markScenarioFailure();

                if (ReportEngine.isFailureReported()) {
                    ReportEngine.clearFailureReported();
                    return;
                }

                try {

                    String screenshotPath =
                            ScreenshotUtil.capture("Failed_Step");

                    ReportEngine.getCurrentStep().fail(
                            "Step Failed",
                            MediaEntityBuilder
                                    .createScreenCaptureFromPath(screenshotPath)
                                    .build()
                    );

                } catch (Exception e) {

                    ReportEngine.getCurrentStep()
                            .fail(
                                    "Step Failed - Screenshot unavailable: "
                                            + e.getMessage()
                            );
                }
            }

            case SKIPPED -> {

                ReportEngine.getCurrentStep()
                        .skip("Step Skipped");
            }

            case PENDING -> {

                ReportEngine.getCurrentStep()
                        .skip("Step Pending");
            }

            case UNDEFINED -> {

                ReportEngine.getCurrentStep()
                        .skip("Step Undefined");
            }

            case AMBIGUOUS -> {

                ReportEngine.getCurrentStep()
                        .fail("Step Ambiguous");
            }
            default -> {
                ReportEngine.getCurrentStep().getStatus();
            }
        }
    }
    private void handleTestCaseFinished(
            TestCaseFinished event) {

        if (event.getResult()
                .getStatus()
                .isOk()) {

            ReportEngine.markScenarioPassed();

        } else {

            ReportEngine.markScenarioFailed();

        }

    }

}