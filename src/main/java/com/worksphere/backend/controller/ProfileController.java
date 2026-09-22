package com.worksphere.backend.controller;

import com.worksphere.backend.dto.ProfileUpdateRequest;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Get My Profile
    @GetMapping("/me")
    public ResponseEntity<Employee> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        Employee employee = profileService.getMyProfile(email);
        return ResponseEntity.ok(employee);
    }

    // Update My Profile
    @PutMapping("/me")
    public ResponseEntity<Employee> updateMyProfile(Authentication authentication, @Valid @RequestBody ProfileUpdateRequest request) {
        String email = authentication.getName();
        Employee employee = profileService.updateMyProfile(email, request);
        return ResponseEntity.ok(employee);
    }
}