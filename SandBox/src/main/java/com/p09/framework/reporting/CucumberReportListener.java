package com.p09.framework.reporting;

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

    private void handleStepFinished(
            TestStepFinished event) {

        if (!(event.getTestStep()
                instanceof PickleStepTestStep)) {

            return;
        }

        if (event.getResult()
                .getStatus()
                .isOk()) {

            return;
        }

        /*
         * If report.fail() already captured
         * the screenshot, don't capture another one.
         */
        if (ReportEngine.isFailureReported()) {

            ReportEngine.clearFailureReported();

            return;

        }

        try {

            String screenshotPath =
                    ScreenshotUtil.capture(
                            "Failed_Step");

            ReportEngine.getCurrentStep()
                    .fail(
                            "Step Failed",
                            com.aventstack.extentreports
                                    .MediaEntityBuilder
                                    .createScreenCaptureFromPath(
                                            screenshotPath)
                                    .build());

        } catch (Exception e) {

            ReportEngine.getCurrentStep()
                    .fail(
                            "Step Failed - Screenshot unavailable: "
                                    + e.getMessage());

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