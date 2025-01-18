package com.elva.pms.pojo.request;

import com.elva.pms.enums.EntityType;
import com.elva.pms.enums.FileCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentUploadRequest {
    private Long entityId;
    private EntityType entityType;
    private FileCategory fileCategory;
    private MultipartFile file;
} 