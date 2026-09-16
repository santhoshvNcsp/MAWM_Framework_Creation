package com.p09.framework.execution;

import com.p09.framework.config.ConfigManager;
import com.p09.framework.utilities.ExcelReader;

import java.util.ArrayList;
import java.util.List;

public class RunManager {

    public List<RunDetails> getExecutableScenarios() {

        List<RunDetails> executableScenarios = new ArrayList<>();

        ExcelReader excelReader = new ExcelReader();

        String sheetName = ConfigManager.get("run.manager.sheet");

        int rowCount = excelReader.getRowCount(sheetName);

        for (int row = 1; row <= rowCount; row++) {

            String execute =
                    excelReader.getCellData(sheetName, row, 0);

            if (!"Y".equalsIgnoreCase(execute)) {
                continue;
            }

            String featureFile =
                    excelReader.getCellData(sheetName, row, 1);

            String scenarioName =
                    excelReader.getCellData(sheetName, row, 2);

            String browser =
                    excelReader.getCellData(sheetName, row, 3);

            executableScenarios.add(
                    new RunDetails(featureFile, scenarioName, browser));

        }

        return executableScenarios;

    }

}