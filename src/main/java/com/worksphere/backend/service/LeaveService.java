package com.worksphere.backend.service;

import com.worksphere.backend.dto.LeaveRequest;
import com.worksphere.backend.dto.LeaveResponse;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.entity.Leave;
import com.worksphere.backend.entity.User;
import com.worksphere.backend.enums.AuditAction;
import com.worksphere.backend.enums.LeaveStatus;
import com.worksphere.backend.exception.BadRequestException;
import com.worksphere.backend.exception.ResourceNotFoundException;
import com.worksphere.backend.repository.EmployeeRepository;
import com.worksphere.backend.repository.LeaveRepository;
import com.worksphere.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    public LeaveService(LeaveRepository leaveRepository, EmployeeRepository employeeRepository,
                        UserRepository userRepository, AuditLogService auditLogService) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    public LeaveResponse applyLeave(String email, LeaveRequest request) {
        Employee employee = getEmployeeByEmail(email);

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new BadRequestException("Start date must be before end date");
        }

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());
        leave.setLeaveType(request.getLeaveType());
        leave.setStatus(LeaveStatus.PENDING);

        Leave saved = leaveRepository.save(leave);
        return mapToResponse(saved);
    }

    public List<LeaveResponse> getMyLeaves(String email) {
        Employee employee = getEmployeeByEmail(email);
        return leaveRepository.findByEmployeeId(employee.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<LeaveResponse> getAllLeaves() {
        return leaveRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LeaveResponse approveLeave(Long id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found with id: " + id));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new BadRequestException("Leave has already been processed");
        }

        leave.setStatus(LeaveStatus.APPROVED);
        Leave saved = leaveRepository.save(leave);

        auditLogService.logAction(AuditAction.APPROVE, "Leave", leave.getEmployee().getEmail());

        return mapToResponse(saved);
    }

    public LeaveResponse rejectLeave(Long id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found with id: " + id));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new BadRequestException("Leave has already been processed");
        }

        leave.setStatus(LeaveStatus.REJECTED);
        Leave saved = leaveRepository.save(leave);

        auditLogService.logAction(AuditAction.REJECT, "Leave", leave.getEmployee().getEmail());

        return mapToResponse(saved);
    }

    // Cancel Leave (self-service, owner only, PENDING only)
    public LeaveResponse cancelLeave(String email, Long id) {
        Employee employee = getEmployeeByEmail(email);

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found with id: " + id));

        if (!leave.getEmployee().getId().equals(employee.getId())) {
            throw new BadRequestException("You can only cancel your own leave requests");
        }

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new BadRequestException("Only pending leaves can be cancelled");
        }

        leave.setStatus(LeaveStatus.CANCELLED);
        Leave saved = leaveRepository.save(leave);
        return mapToResponse(saved);
    }

    private Employee getEmployeeByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return employeeRepository.findById(user.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("No employee linked to this account"));
    }

    private LeaveResponse mapToResponse(Leave leave) {
        LeaveResponse response = new LeaveResponse();
        response.setId(leave.getId());
        response.setEmployeeId(leave.getEmployee().getId());
        response.setEmployeeName(leave.getEmployee().getFirstName() + " " + leave.getEmployee().getLastName());
        response.setStartDate(leave.getStartDate());
        response.setEndDate(leave.getEndDate());
        response.setReason(leave.getReason());
        response.setLeaveType(leave.getLeaveType());
        response.setStatus(leave.getStatus());
        return response;
    }
}

