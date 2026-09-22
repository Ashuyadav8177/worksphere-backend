package com.worksphere.backend.service;

import com.worksphere.backend.dto.DocumentRequest;
import com.worksphere.backend.dto.DocumentResponse;
import com.worksphere.backend.entity.Document;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.entity.User;
import com.worksphere.backend.exception.ResourceNotFoundException;
import com.worksphere.backend.repository.DocumentRepository;
import com.worksphere.backend.repository.EmployeeRepository;
import com.worksphere.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    private final Path uploadDirectory =
            Paths.get("uploads/documents");

    public DocumentService(
            DocumentRepository documentRepository,
            EmployeeRepository employeeRepository,
            UserRepository userRepository
    ) {
        this.documentRepository = documentRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    // Existing URL-based document method
    public DocumentResponse addDocument(
            String email,
            DocumentRequest request
    ) {
        Employee employee = getEmployeeByEmail(email);

        Document document = new Document();

        document.setEmployee(employee);
        document.setDocumentName(request.getDocumentName());
        document.setDocumentType(request.getDocumentType());
        document.setFileUrl(request.getFileUrl());

        Document saved = documentRepository.save(document);

        return mapToResponse(saved);
    }

    // Actual file upload
    public DocumentResponse uploadDocument(
            String email,
            MultipartFile file
    ) {

        // Check file exists
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please select a file"
            );
        }

        // Only PDF files allowed
        if (!"application/pdf".equalsIgnoreCase(
                file.getContentType()
        )) {
            throw new IllegalArgumentException(
                    "Only PDF files are allowed"
            );
        }

        // Maximum file size = 10 MB
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "File size must not exceed 10 MB"
            );
        }

        Employee employee = getEmployeeByEmail(email);

        try {

            // Create upload directory if it does not exist
            Files.createDirectories(uploadDirectory);

            // Get original file name
            String originalFileName =
                    file.getOriginalFilename();

            if (originalFileName == null ||
                    originalFileName.isBlank()) {

                throw new IllegalArgumentException(
                        "Invalid file name"
                );
            }

            // Get file extension
            String extension = "";

            int lastDot =
                    originalFileName.lastIndexOf(".");

            if (lastDot >= 0) {
                extension =
                        originalFileName.substring(lastDot)
                                .toLowerCase();
            }

            // Extra extension validation
            if (!".pdf".equals(extension)) {
                throw new IllegalArgumentException(
                        "Only PDF files are allowed"
                );
            }

            // Generate unique file name
            String uniqueFileName =
                    UUID.randomUUID() + extension;

            // Final file path
            Path filePath =
                    uploadDirectory.resolve(uniqueFileName);

            // Save physical file
            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Create document entity
            Document document = new Document();

            document.setEmployee(employee);

            document.setDocumentName(
                    originalFileName
            );

            document.setDocumentType(
                    file.getContentType()
            );

            /*
             * Relative URL stored in database.
             *
             * Example:
             * /uploads/documents/abc123.pdf
             */
            document.setFileUrl(
                    "/uploads/documents/" + uniqueFileName
            );

            Document saved =
                    documentRepository.save(document);

            return mapToResponse(saved);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to upload file",
                    e
            );
        }
    }

    // Get logged-in employee documents
    public List<DocumentResponse> getMyDocuments(
            String email
    ) {

        Employee employee =
                getEmployeeByEmail(email);

        return documentRepository
                .findByEmployeeId(employee.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Find employee using logged-in user's email
    private Employee getEmployeeByEmail(String email) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        return employeeRepository
                .findById(user.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No employee linked to this account"
                        )
                );
    }

    // Entity -> Response
    private DocumentResponse mapToResponse(
            Document document
    ) {

        DocumentResponse response =
                new DocumentResponse();

        response.setId(document.getId());

        response.setDocumentName(
                document.getDocumentName()
        );

        response.setDocumentType(
                document.getDocumentType()
        );

        response.setFileUrl(
                document.getFileUrl()
        );

        return response;
    }
}