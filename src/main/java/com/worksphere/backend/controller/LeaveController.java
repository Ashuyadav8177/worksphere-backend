package com.worksphere.backend.controller;

import com.worksphere.backend.dto.LeaveRequest;
import com.worksphere.backend.dto.LeaveResponse;
import com.worksphere.backend.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    // Apply Leave
    @PostMapping("/apply")
    public ResponseEntity<LeaveResponse> applyLeave(Authentication authentication, @Valid @RequestBody LeaveRequest request) {
        String email = authentication.getName();
        LeaveResponse response = leaveService.applyLeave(email, request);
        return ResponseEntity.ok(response);
    }

    // Get My Leaves
    @GetMapping("/my")
    public ResponseEntity<List<LeaveResponse>> getMyLeaves(Authentication authentication) {
        String email = authentication.getName();
        List<LeaveResponse> response = leaveService.getMyLeaves(email);
        return ResponseEntity.ok(response);
    }

    // Get All Leaves (ADMIN/MANAGER only)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<LeaveResponse>> getAllLeaves() {
        List<LeaveResponse> response = leaveService.getAllLeaves();
        return ResponseEntity.ok(response);
    }

    // Approve Leave (ADMIN/MANAGER only)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveResponse> approveLeave(@PathVariable Long id) {
        LeaveResponse response = leaveService.approveLeave(id);
        return ResponseEntity.ok(response);
    }

    // Reject Leave (ADMIN/MANAGER only)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveResponse> rejectLeave(@PathVariable Long id) {
        LeaveResponse response = leaveService.rejectLeave(id);
        return ResponseEntity.ok(response);
    }
    // Cancel Leave
    @PutMapping("/{id}/cancel")
    public ResponseEntity<LeaveResponse> cancelLeave(Authentication authentication, @PathVariable Long id) {
        String email = authentication.getName();
        LeaveResponse response = leaveService.cancelLeave(email, id);
        return ResponseEntity.ok(response);
    }
}
