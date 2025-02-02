package com.elva.pms.service.impl;

import com.elva.pms.enums.EntityType;
import com.elva.pms.enums.FileCategory;
import com.elva.pms.pojo.dao.DocumentDao;
import com.elva.pms.jdbc.DocumentJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl {

    @Autowired
    private DocumentJdbcRepository documentRepository;
    
    @Value("${file.upload.directory:/tmp/uploads}")
    private String uploadDirectory;
    
    @Transactional
    public DocumentDao uploadFile(MultipartFile file, Long entityId, EntityType entityType, FileCategory fileCategory) {
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            
            // Save file to disk
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath);
            
            // Create document record
            DocumentDao documentDao = new DocumentDao();
            documentDao.setEntityId(entityId);
            documentDao.setEntityType(entityType);
            documentDao.setFileCategory(fileCategory);
            documentDao.setFileName(originalFilename);
            documentDao.setFilePath(filePath.toString());
            documentDao.setActive(true);
            documentDao.setCreatedBy("system"); // TODO: Get from security context
            documentDao.setCreatedAt(LocalDateTime.now());
            
            // Save to database
            long documentId = documentRepository.insert(documentDao);
            documentDao.setId(documentId);
            
            return documentDao;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
    
    public List<DocumentDao> getDocuments(Long entityId, EntityType entityType) {
        return documentRepository.findByEntityIdAndType(entityId, entityType);
    }
    
    @Transactional
    public void deleteDocument(Long id) {
        // TODO: Implement soft delete in repository
        // Also consider deleting the physical file
        throw new UnsupportedOperationException("Delete operation not implemented");
    }
    
    private void validateFile(MultipartFile file) {
        // TODO: Implement file validation
        // Check file size
        // Check file type
        // Check for malware
        // etc.
    }
} 