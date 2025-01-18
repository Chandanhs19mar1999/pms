package com.elva.pms.pojo.response;

import com.elva.pms.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PlotResponse {
    private Long id;
    private Long landProjectId;
    private String plotNumber;
    private Double size;
    private BigDecimal price;
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
    private String landProjectName;
    private String locationName;
    private Integer documentCount;
} 