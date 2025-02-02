package com.elva.pms.pojo.dao;

import com.elva.pms.enums.PlotStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PlotDao {
    private Long id;
    private Long landProjectId;
    private String plotNumber;
    private Double size;
    private BigDecimal price;
    private PlotStatus plotStatus;
    private String metadata;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
} 