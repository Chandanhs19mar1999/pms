package com.elva.pms.service;

import com.elva.pms.pojo.dao.Plot;
import com.elva.pms.pojo.request.PlotRequest;
import com.elva.pms.jdbc.PlotJdbcRepository;
import com.elva.pms.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PlotService {

    @Autowired
    private PlotJdbcRepository plotRepository;
    
    public Plot createPlot(PlotRequest request) {
        Plot plot = new Plot();
        // Map request to plot
        plot.setLandProjectId(request.getLandProjectId());
        plot.setPlotNumber(request.getPlotNumber());
        plot.setSize(request.getSize());
        plot.setPrice(request.getPrice());
        plot.setMetadata(request.getMetadata());
        plot.setStatus(Status.AVAILABLE);
        
        plotRepository.insert(plot);
        return plot;
    }
    
    public Plot updatePlot(Long id, PlotRequest request) {
        Plot plot = new Plot();
        plot.setId(id);
        // Map request to plot
        plot.setLandProjectId(request.getLandProjectId());
        plot.setPlotNumber(request.getPlotNumber());
        plot.setSize(request.getSize());
        plot.setPrice(request.getPrice());
        plot.setMetadata(request.getMetadata());
        
        plotRepository.update(plot);
        return plot;
    }
    
    public List<Plot> createPlotsFromFile(MultipartFile file) {
        // Implement file processing logic
        // Read CSV/Excel file and create plots
        return null;
    }
    
    public List<Plot> filterPlots(Long landProjectId, String plotNumber) {
        // Implement filtering logic
        return plotRepository.findAll();
    }
} 