package com.elva.pms.pojo.response;

import com.elva.pms.enums.LandProjectStatus;
import com.elva.pms.enums.PlotStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LandProjectResponse {
    private Long id;
    private Long developerId;
    private String name;
    private String description;
    private Long locationId;
    private LandProjectStatus status;
    private String metadata;
    private boolean isActive;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    private Long createdBy;
    private Long updatedBy;

    private Long totalPlots;
    private String locationName;
} 