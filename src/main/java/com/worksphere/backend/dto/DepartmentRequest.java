package com.worksphere.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequest {
    @NotBlank(message = "department name is required")
    private String name;

    private String description;
}
