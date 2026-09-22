package com.worksphere.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRequest {
    @NotBlank
    private String documentName;
    @NotBlank
    private String documentType;
    @NotBlank
    private String fileUrl;
}
