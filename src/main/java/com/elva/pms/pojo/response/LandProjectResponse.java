package com.elva.pms.pojo.response;

import com.elva.pms.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LandProjectResponse {
    private Long id;
    private Long developId;
    private String name;
    private String description;
    private Long locationId;
    private Status status;
    private String metadata;
    private boolean isActive;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    private String createdBy;
    private String updatedBy;
    
    // Additional fields that might be needed in response
    private Integer totalPlots;
    private Integer availablePlots;
    private String locationName; // If you want to include location details
} 