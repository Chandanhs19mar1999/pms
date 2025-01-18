package com.elva.pms.controller;

import com.elva.pms.pojo.request.PlotRequest;
import com.elva.pms.pojo.response.ApiResponse;
import com.elva.pms.service.PlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/plat/management")
@RequiredArgsConstructor
public class PlotController {
    
    private final PlotService plotService;
    
    @PostMapping("/plots")
    public ResponseEntity<?> createPlot(@RequestBody PlotRequest request) {
        return ResponseEntity.ok(ApiResponse.success(plotService.createPlot(request)));
    }
    
    @PutMapping("/plots/{id}")
    public ResponseEntity<?> updatePlot(@PathVariable Long id, @RequestBody PlotRequest request) {
        return ResponseEntity.ok(ApiResponse.success(plotService.updatePlot(id, request)));
    }
    
    @PostMapping("/plots/file-upload")
    public ResponseEntity<?> uploadPlotsFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(ApiResponse.success(plotService.createPlotsFromFile(file)));
    }
    
    @GetMapping("/plots/filter")
    public ResponseEntity<?> filterPlots(
            @RequestParam(required = false) Long landProjectId,
            @RequestParam(required = false) String plotNumber) {
        return ResponseEntity.ok(ApiResponse.success(plotService.filterPlots(landProjectId, plotNumber)));
    }
} 