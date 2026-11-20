package com.qa.framework.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/** Reads a worksheet from src/test/resources/testdata as rows of strings. The first row is treated as the header and skipped. */
public final class ExcelUtils {

    private ExcelUtils() {
    }

    public static List<String[]> readRows(String fileName, String sheetName) {
        String path = "testdata/" + fileName;
        try (InputStream in = ExcelUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalArgumentException("Test data file not found: " + path);
            }
            try (Workbook workbook = WorkbookFactory.create(in)) {
                Sheet sheet = workbook.getSheet(sheetName);
                DataFormatter formatter = new DataFormatter();
                List<String[]> rows = new ArrayList<>();
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    String[] values = new String[row.getLastCellNum()];
                    for (int c = 0; c < values.length; c++) {
                        Cell cell = row.getCell(c);
                        values[c] = cell == null ? "" : formatter.formatCellValue(cell);
                    }
                    rows.add(values);
                }
                return rows;
            }
        } catch (Exception e) {
            throw new IllegalStateException("Could not read " + path, e);
        }
    }
}
