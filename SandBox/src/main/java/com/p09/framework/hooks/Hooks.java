package com.p09.framework.hooks;

import com.p09.framework.config.ConfigManager;
import com.p09.framework.context.ScenarioContext;
import com.p09.framework.driver.DriverManager;
import com.p09.framework.reporting.ReportEngine;
import com.p09.framework.reporting.ReportManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    private final ReportManager report =
            ReportManager.getInstance();

    @Before(order = 0)
    public void initializeReport() {

        report.initialize();
    }

    @Before(order = 1)
    public void beforeScenario(
            Scenario scenario) {
        DriverManager.initializeDriver();
        DriverManager.getDriver()
                .get(
                        ConfigManager.get("url")
                );
        report.createScenario(
                scenario.getName()
        );
    }

    @After(order = 1)
    public void afterScenario(
            Scenario scenario) {
        boolean cucumberFailed =
                scenario.isFailed();

        boolean reportFailed =
                ReportEngine.isScenarioFailed();
        boolean finalFailed =
                cucumberFailed
                        || reportFailed;
        if (finalFailed) {
            ReportEngine.markScenarioFailed();
        } else {
            ReportEngine.markScenarioPassed();
        }
        ReportEngine.recordScenarioResult(finalFailed);

        try {
            DriverManager.quitDriver();
        } finally {
            ScenarioContext.clear();
            ReportEngine.unload();
        }
        if (reportFailed && !cucumberFailed) {
            throw new AssertionError(
                    "Scenario failed because one or more report validations failed."
            );
        }
    }
    @After(order = 0)
    public void flushReport() {
        report.flush();
    }
}