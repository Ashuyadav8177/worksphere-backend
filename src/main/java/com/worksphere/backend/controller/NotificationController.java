package com.worksphere.backend.controller;

import com.worksphere.backend.dto.NotificationResponse;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.exception.ResourceNotFoundException;
import com.worksphere.backend.repository.EmployeeRepository;
import com.worksphere.backend.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final EmployeeRepository employeeRepository;

    public NotificationController(NotificationService notificationService, EmployeeRepository employeeRepository) {
        this.notificationService = notificationService;
        this.employeeRepository = employeeRepository;
    }

    // Get My Notifications
    @GetMapping("/my")
    public ResponseEntity<List<NotificationResponse>> getMyNotifications(Authentication authentication) {
        String email = authentication.getName();
        List<NotificationResponse> response = notificationService.getMyNotifications(email);
        return ResponseEntity.ok(response);
    }

    // Mark Notification as Read
    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long id) {
        NotificationResponse response = notificationService.markAsRead(id);
        return ResponseEntity.ok(response);
    }

    // Test endpoint: manually create a notification (ADMIN only, for testing)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/test")
    public ResponseEntity<String> testNotification(@RequestParam Long employeeId, @RequestParam String message) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        notificationService.createNotification(employee, message);
        return ResponseEntity.ok("Notification created");
    }
}
