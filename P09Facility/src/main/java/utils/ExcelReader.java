package utils;

import model.InboundData;
import model.InboundItem;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public static List<InboundData> readInboundData(String filePath) {

        List<InboundData> inboundDataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

                Row row = sheet.getRow(rowIndex);

                if (row == null) {
                    continue;
                }

                String ilpn = getCellValue(row.getCell(0));
                String itemValues = getCellValue(row.getCell(1));
                String quantityValues = getCellValue(row.getCell(2));
                String location = getCellValue(row.getCell(3));

                if (ilpn.isBlank()) {
                    continue;
                }

                String[] items = itemValues.split(",");
                String[] quantities = quantityValues.split(",");

                if (items.length != quantities.length) {
                    throw new IllegalArgumentException(
                            "ITEM and QUANTITY count mismatch at Excel row "
                                    + (rowIndex + 1)
                                    + ". Items: " + items.length
                                    + ", Quantities: " + quantities.length
                    );
                }

                List<InboundItem> inboundItems = new ArrayList<>();

                for (int i = 0; i < items.length; i++) {

                    String item = items[i].trim();
                    String quantity = quantities[i].trim();

                    inboundItems.add(
                            new InboundItem(item, quantity)
                    );
                }

                InboundData inboundData =
                        new InboundData(
                                ilpn,
                                location,
                                inboundItems
                        );

                inboundDataList.add(inboundData);
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to read Excel file: " + filePath,
                    e
            );
        }

        return inboundDataList;
    }

    private static String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();

        return formatter.formatCellValue(cell).trim();
    }
}