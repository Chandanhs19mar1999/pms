package com.elva.pms.pojo.dao;

import com.elva.pms.enums.Status;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LandProject {
    private Long id;
    private Long developId;
    private String name;
    private String description;
    private Long locationId;
    private Status status;
    private String metadata;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 