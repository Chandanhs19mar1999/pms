package com.elva.pms.controller;

import com.elva.pms.pojo.request.PlotFilterRequest;
import com.elva.pms.pojo.request.PlotRequest;
import com.elva.pms.pojo.response.ApiResponse;
import com.elva.pms.pojo.response.PlotResponse;
import com.elva.pms.service.impl.PlotServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/plat/management")
@RequiredArgsConstructor
public class PlotController {
    
    private final PlotServiceImpl plotServiceImpl;
    
    @PostMapping("/plot")
    public ResponseEntity<ApiResponse<PlotResponse>> createPlot(@RequestBody PlotRequest request) {
        PlotResponse plot = plotServiceImpl.createPlot(request);
        return new ResponseEntity<>(ApiResponse.success(plot), HttpStatus.OK);
    }
    
    @PutMapping("/plot/{id}")
    public ResponseEntity<ApiResponse<PlotResponse>> updatePlot(@PathVariable Long id, @RequestBody PlotRequest request) {
        PlotResponse plot =  plotServiceImpl.updatePlot(id, request);
        return new ResponseEntity<>(ApiResponse.success(plot), HttpStatus.OK);
    }
    
    @PostMapping("/plot/file-upload")
    public ResponseEntity<?> uploadPlotsFile(@RequestParam("file") MultipartFile file) {
        plotServiceImpl.createPlotsFromFile(file);
        return ResponseEntity.ok(ApiResponse.success("success fully processed the file"));
    }
    
    @GetMapping("/plot/filter")
    public ResponseEntity<ApiResponse<List<PlotResponse>>> filterPlots(
            @RequestParam(required = false) Long plotId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long landProjectId,
            @RequestParam(required = false) String plotNumber) {

        List<PlotResponse> plotResponses = plotServiceImpl.filterPlots(PlotFilterRequest.builder()
                .plotId(plotId)
                .status(status)
                .landProjectId(landProjectId)
                .plotNumber(plotNumber)
                .build());

        return new ResponseEntity<>(ApiResponse.success(plotResponses), HttpStatus.OK);
    }
} 