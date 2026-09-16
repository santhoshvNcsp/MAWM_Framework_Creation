package com.p09.framework.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.p09.framework.config.ConfigManager;

public class ExcelReader {

    private XSSFWorkbook workbook;

    public ExcelReader() {

        String path = ConfigManager.get("run.manager.path");

        try {

            File excelFile = new File(path);

            System.out.println("Excel Path : " + path);
            System.out.println("Exists : " + excelFile.exists());
            System.out.println("Length : " + excelFile.length());
            System.out.println("Absolute Path : " + excelFile.getAbsolutePath());

            if (!excelFile.exists()) {
                throw new RuntimeException(
                        "RunManager Excel file not found.\nLocation : "
                                + excelFile.getAbsolutePath());
            }

            if (excelFile.length() == 0) {
                throw new RuntimeException(
                        "RunManager.xlsx is empty (0 bytes).\n"
                                + "Please create a valid Excel workbook and add the RunManager sheet.");
            }

            FileInputStream file = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(file);
            file.close();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load Excel file : "
                            + path,
                    e);
        }
    }

    public int getRowCount(String sheetName) {
        return workbook.getSheet(sheetName).getLastRowNum();
    }

    public String getCellData(String sheetName, int row, int column) {
        return workbook.getSheet(sheetName)
                .getRow(row)
                .getCell(column)
                .toString();
    }

    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}