package com.worksphere.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequest {

    @NotBlank
    private String phone;
}
