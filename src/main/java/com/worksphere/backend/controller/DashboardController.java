package com.worksphere.backend.controller;

import com.worksphere.backend.dto.DashboardResponse;
import com.worksphere.backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/summary")
    public ResponseEntity<DashboardResponse> getSummary() {
        DashboardResponse response = dashboardService.getSummary();
        return ResponseEntity.ok(response);
    }
}