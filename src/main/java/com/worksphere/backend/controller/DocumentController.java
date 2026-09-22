package com.worksphere.backend.controller;

import com.worksphere.backend.dto.DocumentRequest;
import com.worksphere.backend.dto.DocumentResponse;
import com.worksphere.backend.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // Add document using existing file URL
    @PostMapping
    public ResponseEntity<DocumentResponse> addDocument(
            Authentication authentication,
            @Valid @RequestBody DocumentRequest request
    ) {
        String email = authentication.getName();

        DocumentResponse response =
                documentService.addDocument(email, request);

        return ResponseEntity.ok(response);
    }

    // Upload actual file from React File Explorer
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponse> uploadDocument(
            Authentication authentication,
            @RequestParam("file") MultipartFile file
    ) {
        String email = authentication.getName();

        DocumentResponse response =
                documentService.uploadDocument(email, file);

        return ResponseEntity.ok(response);
    }

    // Get logged-in employee's documents
    @GetMapping("/my")
    public ResponseEntity<List<DocumentResponse>> getMyDocuments(
            Authentication authentication
    ) {
        String email = authentication.getName();

        List<DocumentResponse> response =
                documentService.getMyDocuments(email);

        return ResponseEntity.ok(response);
    }
}
