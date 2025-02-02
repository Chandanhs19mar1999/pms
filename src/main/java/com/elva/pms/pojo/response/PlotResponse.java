package com.elva.pms.pojo.response;

import com.elva.pms.enums.PlotStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlotResponse {
    private Long id;
    private Long landProjectId;
    private String plotNumber;
    private Double size;
    private BigDecimal price;
    private PlotStatus plotStatus;
    private String metadata;
    private boolean isActive;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    private Long createdBy;
    private Long updatedBy;
    
    // Additional fields that might be needed in response
    private String landProjectName;
    private String locationName;
    private Integer documentCount;
} 