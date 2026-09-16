package com.p09.framework.execution;

public class RunDetails {

    private final String featureFile;
    private final String scenarioName;
    private String browser;

    public RunDetails(String featureFile,
                      String scenarioName,
                      String browser) {

        this.featureFile = featureFile;
        this.scenarioName = scenarioName;
        this.browser = browser;

    }

    public String getFeatureFile() {
        return featureFile;
    }
    public String getBrowser() {
        return browser;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    @Override
    public String toString() {
        return "RunDetails{" +
                "featureFile='" + featureFile + '\'' +
                ", scenarioName='" + scenarioName + '\'' +
                '}';
    }
}