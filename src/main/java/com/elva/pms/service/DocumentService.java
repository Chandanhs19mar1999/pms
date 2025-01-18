package com.elva.pms.service;

import com.elva.pms.enums.EntityType;
import com.elva.pms.enums.FileCategory;
import com.elva.pms.pojo.dao.Document;
import com.elva.pms.jdbc.DocumentJdbcRepository;
import lombok.RequiredArgsConstructor;
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
public class DocumentService {

    @Autowired
    private DocumentJdbcRepository documentRepository;
    
    @Value("${file.upload.directory:/tmp/uploads}")
    private String uploadDirectory;
    
    @Transactional
    public Document uploadFile(MultipartFile file, Long entityId, EntityType entityType, FileCategory fileCategory) {
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
            Document document = new Document();
            document.setEntityId(entityId);
            document.setEntityType(entityType);
            document.setFileCategory(fileCategory);
            document.setFileName(originalFilename);
            document.setFilePath(filePath.toString());
            document.setActive(true);
            document.setCreatedBy("system"); // TODO: Get from security context
            document.setCreatedAt(LocalDateTime.now());
            
            // Save to database
            long documentId = documentRepository.insert(document);
            document.setId(documentId);
            
            return document;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
    
    public List<Document> getDocuments(Long entityId, EntityType entityType) {
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