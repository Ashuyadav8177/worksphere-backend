package com.worksphere.backend.service;

import com.worksphere.backend.dto.AttendanceResponse;
import com.worksphere.backend.entity.Attendance;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.entity.User;
import com.worksphere.backend.enums.AttendanceStatus;
import com.worksphere.backend.exception.BadRequestException;
import com.worksphere.backend.exception.ResourceNotFoundException;
import com.worksphere.backend.repository.AttendanceRepository;
import com.worksphere.backend.repository.EmployeeRepository;
import com.worksphere.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    public AttendanceResponse checkIn(String email) {
        Employee employee = getEmployeeByEmail(email);
        LocalDate today = LocalDate.now();

        attendanceRepository.findByEmployeeIdAndDate(employee.getId(), today)
                .ifPresent(a -> {
                    throw new BadRequestException("Already checked in today");
                });

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(today);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.PRESENT);

        Attendance saved = attendanceRepository.save(attendance);
        return mapToResponse(saved);
    }

    public AttendanceResponse checkOut(String email) {
        Employee employee = getEmployeeByEmail(email);
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(employee.getId(), today)
                .orElseThrow(() -> new BadRequestException("Please check-in first"));

        if (attendance.getCheckOutTime() != null) {
            throw new BadRequestException("Already checked out today");
        }

        attendance.setCheckOutTime(LocalDateTime.now());
        Attendance saved = attendanceRepository.save(attendance);
        return mapToResponse(saved);
    }

    public List<AttendanceResponse> getMyAttendance(String email) {
        Employee employee = getEmployeeByEmail(email);
        return attendanceRepository.findByEmployeeId(employee.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AttendanceResponse> getAttendanceByEmployeeId(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AttendanceResponse> getAllAttendance() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Employee getEmployeeByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return employeeRepository.findById(user.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("No employee linked to this account"));
    }

    private AttendanceResponse mapToResponse(Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setId(attendance.getId());
        response.setEmployeeId(attendance.getEmployee().getId());
        response.setEmployeeName(attendance.getEmployee().getFirstName() + " " + attendance.getEmployee().getLastName());
        response.setDate(attendance.getDate());
        response.setCheckInTime(attendance.getCheckInTime());
        response.setCheckOutTime(attendance.getCheckOutTime());
        response.setStatus(attendance.getStatus());
        return response;
    }
}
