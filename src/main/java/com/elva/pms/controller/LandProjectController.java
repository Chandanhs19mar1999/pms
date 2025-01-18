package com.elva.pms.controller;

import com.elva.pms.pojo.request.LandProjectRequest;
import com.elva.pms.pojo.response.LandProjectResponse;
import com.elva.pms.service.LandProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plat/management")
@RequiredArgsConstructor
public class LandProjectController {
    
    private final LandProjectService landProjectService;
    
    @PostMapping("/land-projects")
    public ResponseEntity<LandProjectResponse> createLandProject(@RequestBody LandProjectRequest request) {
        return ResponseEntity.ok(landProjectService.createLandProject(request));
    }
    
    @PutMapping("/land-projects/{id}")
    public ResponseEntity<LandProjectResponse> updateLandProject(
            @PathVariable Long id,
            @RequestBody LandProjectRequest request) {
        //landProjectService.updateLandProject(id, request)
        return ResponseEntity.ok(new LandProjectResponse());
    }
    
    @GetMapping("/land-project/filter")
    public ResponseEntity<?> filterLandProjects(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long locationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        //landProjectService.filterLandProjects(name, locationId, page, size)
        return ResponseEntity.ok("implementation in progress");
    }
} 