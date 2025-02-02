package com.elva.pms.service.impl;

import com.elva.pms.converter.PlotConverter;
import com.elva.pms.pojo.dao.PlotDao;
import com.elva.pms.pojo.request.PlotFilterRequest;
import com.elva.pms.pojo.request.PlotRequest;
import com.elva.pms.jdbc.PlotJdbcRepository;
import com.elva.pms.enums.PlotStatus;
import com.elva.pms.pojo.response.PlotResponse;
import com.elva.pms.utils.CsvFileUtils;
import com.elva.pms.utils.ExcelFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@Service
public class PlotServiceImpl {

    @Autowired
    private PlotJdbcRepository plotRepository;
    
    public PlotResponse createPlot(PlotRequest request) {
        request.setStatus(PlotStatus.AVAILABLE.name());
        PlotDao plotDao = PlotConverter.convertRequestToDao(request);
        List<Long> plotIdList = plotRepository.bulkInsert(List.of(plotDao));
        plotDao.setId(plotIdList.getFirst());
        return PlotConverter.convertDaoToResponse(plotDao);
    }
    
    public PlotResponse updatePlot(Long id, PlotRequest request) {
        PlotDao plotDao = PlotConverter.convertRequestToDao(request);
        plotDao.setId(id);
        plotRepository.update(plotDao);
        return PlotConverter.convertDaoToResponse(plotDao);
    }
    
    public void createPlotsFromFile(MultipartFile file) {
        List<PlotRequest> plotRequests = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        
        if (fileName != null && fileName.toLowerCase().endsWith(".xlsx")) {
            plotRequests = ExcelFileUtils.importPlotDataFromExcel(file);
        } else if (fileName != null && fileName.toLowerCase().endsWith(".csv")) {
            plotRequests = CsvFileUtils.importPlotDataFromCSV(file);
        } else {
            throw new RuntimeException("Unsupported file format. Please upload an Excel (.xlsx) or CSV (.csv) file");
        }
        if (!plotRequests.isEmpty()) {
            List<PlotDao> plotDaoList = PlotConverter.convertRequestToDaoList(plotRequests);
            List<Long> plotIdList = plotRepository.bulkInsert(plotDaoList);
        }
    }
    
    public List<PlotResponse> filterPlots(PlotFilterRequest filterRequest) {
        List<PlotDao> plotsDaoList = plotRepository.getPlotsByFilter(filterRequest);
        if (plotsDaoList.isEmpty()) {
            return null;
        }
        return PlotConverter.getResponseList(plotsDaoList);
    }
} 