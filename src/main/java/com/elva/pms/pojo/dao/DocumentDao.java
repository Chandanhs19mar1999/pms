package com.elva.pms.pojo.dao;

import com.elva.pms.enums.EntityType;
import com.elva.pms.enums.FileCategory;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentDao {
    private Long id;
    private Long entityId;
    private EntityType entityType;
    private FileCategory fileCategory;
    private String fileName;
    private String filePath;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 