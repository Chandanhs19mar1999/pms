package com.elva.pms.utils;

import com.elva.pms.pojo.request.PlotRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelFileUtils {

    public static List<PlotRequest> importPlotDataFromExcel(MultipartFile file) {

        List<PlotRequest> plotRequests = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            Map<String, Integer> headers = new HashMap<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    headers.put(cell.getStringCellValue().toLowerCase(), i);
                }
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                try {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    String landProjectIdStr = getCellValueAsString(row.getCell(headers.get("land_project_id")));
                    String plotNumber = getCellValueAsString(row.getCell(headers.get("plot_number")));
                    String status = getCellValueAsString(row.getCell(headers.get("status")));

                    if (landProjectIdStr == null || plotNumber == null || status == null ||
                            landProjectIdStr.trim().isEmpty() || plotNumber.trim().isEmpty() || status.trim().isEmpty()) {
                        continue;
                    }

                    PlotRequest plotRequest = new PlotRequest();
                    plotRequest.setLandProjectId(Long.parseLong(landProjectIdStr));
                    plotRequest.setPlotNumber(plotNumber);
                    plotRequest.setStatus(status);

                    String sizeStr = getCellValueAsString(row.getCell(headers.get("size")));
                    plotRequest.setSize(sizeStr == null || sizeStr.trim().isEmpty() ? 0.0 : Double.parseDouble(sizeStr));

                    String priceStr = getCellValueAsString(row.getCell(headers.get("price")));
                    plotRequest.setPrice(priceStr == null || priceStr.trim().isEmpty() ?
                            BigDecimal.ZERO : new BigDecimal(priceStr));

                    plotRequests.add(plotRequest);

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            return plotRequests;
        } catch (IOException e) {
            throw new RuntimeException("Failed to process Excel file", e);
        }
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return null;
        }
    }

}
