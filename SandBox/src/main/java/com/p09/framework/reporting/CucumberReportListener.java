package com.p09.framework.reporting;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.ExtentTest;
import com.p09.framework.utilities.ScreenshotUtil;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Status;
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

    private void handleStepStarted(TestStepStarted event) {

        if (!(event.getTestStep() instanceof PickleStepTestStep pickleStep)) {
            return;
        }

        String stepText =
                pickleStep.getStep().getKeyword()
                        + " "
                        + pickleStep.getStep().getText();

        System.out.println(
                "Cucumber Step : " + stepText);

        ReportEngine.createStep(stepText);
        ReportEngine.clearFailureReported();
    }

    private void handleStepFinished(TestStepFinished event) {

        if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            return;
        }

        Status status =
                event.getResult().getStatus();

        System.out.println(
                "STEP STATUS: " + status
        );
        ReportEngine.recordStepResult(status);

        ExtentTest currentStep =
                ReportEngine.getCurrentStep();

        switch (status) {

            case PASSED -> {
            }

            case FAILED -> {

                // A Cucumber failure always means the scenario failed.
                ReportEngine.markScenarioFailure();

                // If ReportManager.fail() already logged the failure,
                // do not create another failure entry.
                if (ReportEngine.isFailureReported()) {
                    ReportEngine.clearFailureReported();
                    return;
                }

                if (currentStep == null) {
                    return;
                }

                try {

                    String screenshotPath =
                            ScreenshotUtil.capture("Failed_Step");

                    currentStep.fail(
                            "Step Failed",
                            MediaEntityBuilder
                                    .createScreenCaptureFromPath(
                                            screenshotPath)
                                    .build()
                    );

                } catch (Exception e) {

                    currentStep.fail(
                            "Step Failed - Screenshot unavailable: "
                                    + e.getMessage()
                    );
                }
            }

            case SKIPPED -> {

                // User requirement:
                // scenario is considered passed only when all
                // report/Cucumber steps are passed.
                ReportEngine.markScenarioFailure();

                if (currentStep != null) {

                    currentStep.skip(
                            "Step Skipped"
                    );
                }
            }

            case PENDING -> {

                ReportEngine.markScenarioFailure();

                if (currentStep != null) {

                    currentStep.skip(
                            "Step Pending"
                    );
                }
            }

            case UNDEFINED -> {

                ReportEngine.markScenarioFailure();

                if (currentStep != null) {

                    currentStep.skip(
                            "Step Undefined"
                    );
                }
            }

            case AMBIGUOUS -> {

                ReportEngine.markScenarioFailure();

                if (currentStep != null) {

                    currentStep.fail(
                            "Step Ambiguous"
                    );
                }
            }

            default -> {

                // Any status other than PASSED should not result
                // in a green scenario.
                if (status != Status.PASSED) {

                    ReportEngine.markScenarioFailure();
                }
            }
        }
    }

    private void handleTestCaseFinished(TestCaseFinished event) {

        boolean cucumberPassed =
                event.getResult()
                        .getStatus()
                        == Status.PASSED;

        boolean reportFailed =
                ReportEngine.isScenarioFailed();

        boolean finalFailed =
                !cucumberPassed
                        || reportFailed;

        if (finalFailed) {

            ReportEngine.markScenarioFailed();

        } else {

            ReportEngine.markScenarioPassed();
        }
        ReportEngine.recordScenarioResult(finalFailed);
    }
}