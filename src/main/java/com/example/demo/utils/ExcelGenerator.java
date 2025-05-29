package com.example.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ExcelGenerator {

    XSSFWorkbook workbook;
    List<XSSFSheet> sheets;

    public ExcelGenerator() {
        this.workbook = new XSSFWorkbook();
        this.sheets = new LinkedList<>();
    }

    public Pair<XSSFSheet, Integer> addSheet(String nombre) {
        XSSFSheet sheet = this.workbook.createSheet(nombre);
        this.sheets.add(sheet);
        return Pair.of(sheet, sheets.size() - 1);
    }

    public XSSFSheet getSheet(int index) {
        if (index < 0 || index >= sheets.size()) {
            throw new IndexOutOfBoundsException("Sheet index out of bounds: " + index);
        }
        return sheets.get(index);
    }

    public void addData(int sheetIndex, List<List<Object>> data) {
        XSSFSheet sheet = getSheet(sheetIndex);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);

        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            List<Object> rowData = data.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                createCell(sheet, row, j, rowData.get(j), style);
            }
        }
    }

    public Resource generateExcel() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        } catch (IOException e) {
            log.error("Error generating Excel file", e);
            throw new RuntimeException("Error generating Excel file", e);
        }
    }

    public void addHeaders(int sheetIndex, String[] headers) {
        XSSFSheet sheet = getSheet(sheetIndex);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        XSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            createCell(sheet, headerRow, i, headers[i], style);
        }
    }

    private void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value == null) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }
}
