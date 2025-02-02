package com.elva.pms.controller;

import com.elva.pms.pojo.request.LandProjectFilterRequest;
import com.elva.pms.pojo.request.LandProjectRequest;
import com.elva.pms.pojo.response.ApiResponse;
import com.elva.pms.pojo.response.LandProjectResponse;
import com.elva.pms.service.LandProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plat/management")
public class LandProjectController {

    @Autowired
    private LandProjectService landProjectService;
    
    @PostMapping("/land-project")
    public ResponseEntity<ApiResponse<LandProjectResponse>> createLandProject(@RequestBody LandProjectRequest request) {
        LandProjectResponse landProjectResponse = landProjectService.createLandProject(request);
        return new ResponseEntity<>(ApiResponse.success(landProjectResponse), HttpStatus.OK);
    }
    
    @PutMapping("/land-project/{id}")
    public ResponseEntity<ApiResponse<LandProjectResponse>> updateLandProject(
            @PathVariable Long id,
            @RequestBody LandProjectRequest request) {
        LandProjectResponse updatedLandProjectResponse = landProjectService.updateLandProject(id, request);
        return new ResponseEntity<>(ApiResponse.success(updatedLandProjectResponse), HttpStatus.OK);
    }
    
    @GetMapping("/land-project/filter")
    public ResponseEntity<ApiResponse<List<LandProjectResponse>>> filterLandProjects(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) Long developerId,
            @RequestParam(required = false) Long projectId,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset)
    {
        LandProjectFilterRequest landProjectFilterRequest = LandProjectFilterRequest.builder()
                .name(name)
                .projectId(projectId)
                .developerId(developerId)
                .limit(limit)
                .offset(offset)
                .build();

        List<LandProjectResponse> responseList = landProjectService.getLandProjectsByFilters(landProjectFilterRequest);
        return new ResponseEntity<>(ApiResponse.success(responseList), HttpStatus.OK);
    }
} 