package com.p09.framework.execution;

import com.p09.framework.config.ConfigManager;
import io.cucumber.core.cli.Main;

import java.nio.file.Paths;

public class CucumberExecutor {

    public boolean execute(RunDetails runDetails) {

        String featurePath = Paths.get(
                        ConfigManager.get("feature.path"),
                        runDetails.getFeatureFile())
                .toString();

        byte exitStatus = Main.run(
                new String[]{
                        featurePath,

                        "--glue",
                        "com.p09.framework.hooks",

                        "--glue",
                        ConfigManager.get("glue.package"),



                        "--name",
                        runDetails.getScenarioName(),

                        "--plugin",
                        "pretty",

                        "--plugin",
                        "com.p09.framework.reporting.CucumberReportListener",
                },
                Thread.currentThread().getContextClassLoader());

        return exitStatus == 0;
    }

}