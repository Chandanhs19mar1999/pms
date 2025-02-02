package com.elva.pms.pojo.dao;

import com.elva.pms.enums.LandProjectStatus;
import com.elva.pms.enums.PlotStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LandProjectDao {
    public Long id;
    public Long developerId;
    public String name;
    public String description;
    public Long locationId;
    public LandProjectStatus status;
    public String metadata;
    public Boolean isActive;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public Long createdBy;
    public Long updatedBy;
    public Long totalPlots;
} 