package com.elva.pms.service;

import com.elva.pms.pojo.dao.LandProject;
import com.elva.pms.pojo.request.LandProjectRequest;
import com.elva.pms.pojo.response.LandProjectResponse;
import com.elva.pms.jdbc.LandProjectJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LandProjectService {

    @Autowired
    private LandProjectJdbcRepository landProjectRepository;
    
    @Transactional
    public LandProjectResponse createLandProject(LandProjectRequest request) {
        LandProject landProject = new LandProject();
        // Map request to DAO
        landProject.setName(request.getName());
        landProject.setDescription(request.getDescription());
        landProject.setLocationId(request.getLocationId());
        landProject.setDevelopId(request.getDevelopId());
        landProject.setMetadata(request.getMetadata());
        landProject.setActive(true);
        
        // Save to database
        landProjectRepository.insert(landProject);
        
        // Convert to response and return
        return convertToResponse(landProject);
    }
    
    private LandProjectResponse convertToResponse(LandProject landProject) {
        // Implementation of conversion logic
        LandProjectResponse response = new LandProjectResponse();
        // Set properties
        return response;
    }
} 