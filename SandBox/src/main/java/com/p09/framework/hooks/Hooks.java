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

        // =========================================================
        // INITIALIZE BROWSER
        // =========================================================

        DriverManager.initializeDriver();

        // =========================================================
        // LAUNCH APPLICATION
        // =========================================================

        DriverManager.getDriver()
                .get(
                        ConfigManager.get("url")
                );

        // =========================================================
        // CREATE SCENARIO IN REPORT
        // =========================================================

        report.createScenario(
                scenario.getName()
        );
    }

    @After(order = 1)
    public void afterScenario(
            Scenario scenario) {

        /*
         * Capture both sources BEFORE unloading ReportEngine.
         */
        boolean cucumberFailed =
                scenario.isFailed();

        boolean reportFailed =
                ReportEngine.isScenarioFailed();

        /*
         * Final scenario result:
         *
         * Cucumber failure OR report failure
         * = scenario failure
         */
        boolean finalFailed =
                cucumberFailed
                        || reportFailed;

        // =========================================================
        // FINAL SCENARIO STATUS IN EXTENT REPORT
        // =========================================================

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

        /*
         * =========================================================
         * IMPORTANT
         * =========================================================
         *
         * If Cucumber itself did not fail, but our report contains
         * a FAIL entry, force the Cucumber scenario to fail.
         *
         * This is what prevents:
         *
         * Report = FAILED
         * Cucumber = PASSED
         *
         * when continue.on.failure=true.
         */
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