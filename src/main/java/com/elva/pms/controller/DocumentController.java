package com.elva.pms.controller;

import com.elva.pms.enums.EntityType;
import com.elva.pms.enums.FileCategory;
import com.elva.pms.pojo.response.ApiResponse;
import com.elva.pms.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/plat/management")
public class DocumentController {

    @Autowired
    private  DocumentService documentService;


    @PostMapping(value = "/file-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("entityId") Long entityId,
            @RequestParam("entityType") EntityType entityType,
            @RequestParam("fileCategory") FileCategory fileCategory) {
        return ResponseEntity.ok(ApiResponse.success(
            documentService.uploadFile(file, entityId, entityType, fileCategory)));
    }
    
    @GetMapping("/documents")
    public ResponseEntity<?> getDocuments(
            @RequestParam("entityId") Long entityId,
            @RequestParam("entityType") EntityType entityType) {
        return ResponseEntity.ok(ApiResponse.success(
            documentService.getDocuments(entityId, entityType)));
    }
    
    @GetMapping("/file-categories")
    public ResponseEntity<?> getFileCategories() {
        return ResponseEntity.ok(ApiResponse.success(FileCategory.values()));
    }
    
    @DeleteMapping("/documents/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok(ApiResponse.success("Document deleted successfully"));
    }
} 