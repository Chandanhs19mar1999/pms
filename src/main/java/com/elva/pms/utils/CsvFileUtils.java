package com.elva.pms.utils;

import com.elva.pms.pojo.request.PlotRequest;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CsvFileUtils {

    public static List<PlotRequest> importPlotDataFromCSV(MultipartFile file) {

        List<PlotRequest> plotRequests = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

            for (CSVRecord record : csvParser) {
                try {
                    String landProjectIdStr = record.get("land_project_id");
                    String plotNumber = record.get("plot_number");
                    String status = record.get("status");

                    // Skip if mandatory fields are missing
                    if (landProjectIdStr == null || plotNumber == null || status == null ||
                            landProjectIdStr.trim().isEmpty() || plotNumber.trim().isEmpty() || status.trim().isEmpty()) {
                        continue;
                    }

                    PlotRequest plotRequest = new PlotRequest();
                    plotRequest.setLandProjectId(Long.parseLong(landProjectIdStr));
                    plotRequest.setPlotNumber(plotNumber);
                    plotRequest.setStatus(status);

                    String sizeStr = record.get("size");
                    plotRequest.setSize(sizeStr == null || sizeStr.trim().isEmpty() ? 0.0 : Double.parseDouble(sizeStr));

                    String priceStr = record.get("price");
                    plotRequest.setPrice(priceStr == null || priceStr.trim().isEmpty() ?
                            BigDecimal.ZERO : new BigDecimal(priceStr));

                    plotRequests.add(plotRequest);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            return plotRequests;
        } catch (IOException e) {
            throw new RuntimeException("Failed to process CSV file", e);
        }
    }



}
