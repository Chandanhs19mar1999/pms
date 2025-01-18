package com.elva.pms.pojo.dao;

import com.elva.pms.enums.Status;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Plot {
    private Long id;
    private Long landProjectId;
    private String plotNumber;
    private Double size;
    private BigDecimal price;
    private Status status;
    private String metadata;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 