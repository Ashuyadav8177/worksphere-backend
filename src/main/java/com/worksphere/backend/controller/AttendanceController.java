package com.worksphere.backend.controller;

import com.worksphere.backend.dto.AttendanceResponse;
import com.worksphere.backend.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // Check-In
    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponse> checkIn(Authentication authentication) {
        String email = authentication.getName();
        AttendanceResponse response = attendanceService.checkIn(email);
        return ResponseEntity.ok(response);
    }

    // Check-Out
    @PutMapping("/check-out")
    public ResponseEntity<AttendanceResponse> checkOut(Authentication authentication) {
        String email = authentication.getName();
        AttendanceResponse response = attendanceService.checkOut(email);
        return ResponseEntity.ok(response);
    }

    // Get My Attendance History
    @GetMapping("/my")
    public ResponseEntity<List<AttendanceResponse>> getMyAttendance(Authentication authentication) {
        String email = authentication.getName();
        List<AttendanceResponse> response = attendanceService.getMyAttendance(email);
        return ResponseEntity.ok(response);
    }

    // Get Attendance By Employee Id (ADMIN/MANAGER only)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByEmployeeId(@PathVariable Long employeeId) {
        List<AttendanceResponse> response = attendanceService.getAttendanceByEmployeeId(employeeId);
        return ResponseEntity.ok(response);
    }

    // Get All Attendance Records (ADMIN/MANAGER only)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> getAllAttendance() {
        List<AttendanceResponse> response = attendanceService.getAllAttendance();
        return ResponseEntity.ok(response);
    }
}
