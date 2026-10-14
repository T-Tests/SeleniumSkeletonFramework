package com.skeleton.utils;

import com.skeleton.config.ConfigManager;
import com.skeleton.exceptions.FrameworkException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Reads .xlsx test data with Apache POI. IMPLEMENTS the TestDataReader interface. */
public class ExcelReader implements TestDataReader {

    private final Path file;
    private final DataFormatter formatter = new DataFormatter(); // reads any cell type as displayed text

    public ExcelReader() {
        this(ConfigManager.getInstance().get("testdata.file"));
    }

    /** Constructor overloading: allows a custom file. */
    public ExcelReader(String filePath) {
        this.file = Path.of(filePath);
    }

    @Override
    public List<Map<String, String>> readSheet(String sheetName) {
        try (var in = Files.newInputStream(file); Workbook workbook = WorkbookFactory.create(in)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new FrameworkException("Sheet not found: " + sheetName + " in " + file);
            }
            Row header = sheet.getRow(0);
            List<Map<String, String>> rows = new ArrayList<>();
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                Map<String, String> data = new LinkedHashMap<>();
                for (int c = 0; c < header.getLastCellNum(); c++) {
                    data.put(formatter.formatCellValue(header.getCell(c)),
                            formatter.formatCellValue(row.getCell(c)));
                }
                rows.add(data);
            }
            return rows;
        } catch (IOException e) {
            throw new FrameworkException("Cannot read Excel file: " + file, e);
        }
    }
}