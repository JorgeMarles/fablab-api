package com.example.demo.services;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.demo.entities.EstadoOfertaFormacion;
import com.example.demo.entities.OfertaFormacion;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReporteService {

    @Autowired
    private OfertaFormacionService ofertaFormacionService;

    public Resource generarReporteCurso() throws Exception {
        XSSFWorkbook workbook = generarExcel();
        XSSFSheet sheet = generarHoja("CURSO", workbook);
        addHeaders(workbook, sheet,
                new String[] { "CODIGO_CURSO", "NOMBRE_CURSO", "ID_CINE_CAMPO_DETALLADO", "ES_EXTENSION", "ACTIVO" });
        int countRow = 1;
        List<OfertaFormacion> ofertas = ofertaFormacionService.listarTodosEntidades();
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (OfertaFormacion of : ofertas) {
            XSSFRow row = sheet.createRow(countRow++);
            createCell(sheet, row, 0, of.getCodigo(), null);
            createCell(sheet, row, 1, of.getNombre(), null);
            createCell(sheet, row, 2, of.getCine(), null);
            createCell(sheet, row, 3, of.isExtension() ? "S" : "N", null);
            createCell(sheet, row, 4, of.getEstado() == EstadoOfertaFormacion.ACTIVA ? "S" : "N", null);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
            return new ByteArrayResource(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("Error generating report: {}", e.getMessage());
            throw e;
        }

    }

    public XSSFWorkbook generarExcel() {
        return new XSSFWorkbook();
    }

    public XSSFSheet generarHoja(String nombre, XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet(nombre);
        return sheet;
    }

    public void addHeaders(XSSFWorkbook workbook, XSSFSheet sheet, String[] headers) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
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
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }
}
