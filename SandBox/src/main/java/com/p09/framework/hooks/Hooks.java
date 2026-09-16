package com.p09.framework.hooks;

import com.p09.framework.config.ConfigManager;
import com.p09.framework.context.ScenarioContext;
import com.p09.framework.driver.BrowserContext;
import com.p09.framework.driver.DriverManager;
import com.p09.framework.reporting.ReportEngine;
import com.p09.framework.reporting.ReportManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    private final ReportManager report = ReportManager.getInstance();

    @Before(order = 0)
    public void initializeReport() {

        report.initialize();

    }

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {

        // Initialize Browser
        DriverManager.initializeDriver();

        // Launch Application
        DriverManager.getDriver().get(ConfigManager.get("url"));

        // Create Scenario in Report
        report.createScenario(scenario.getName());

//        report.info("Scenario Name : " + scenario.getName());

    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {

        if (scenario.isFailed()) {

            ReportEngine.getTest()
                    .fail("Scenario Name : " + scenario.getName());

        } else {

            ReportEngine.getTest()
                    .pass("Scenario Name : " + scenario.getName());

        }

        DriverManager.quitDriver();
        ScenarioContext.clear();
        ReportEngine.unload();
        BrowserContext.clear();
    }

    @After(order = 0)
    public void flushReport() {
        report.flush();

    }

}