package com.elva.pms.pojo.response;

import com.elva.pms.enums.EntityType;
import com.elva.pms.enums.FileCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentResponse {
    private Long id;
    private Long entityId;
    private EntityType entityType;
    private FileCategory fileCategory;
    private String fileName;
    private String filePath;
    private boolean isActive;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    private String createdBy;
    private String updatedBy;
    
    // Additional fields that might be useful in response
    private String fileUrl; // Full URL to access the file
    private Long fileSize; // Size in bytes
    private String fileType; // MIME type
    private String entityName; // Name of the related entity (land project or plot)
} 