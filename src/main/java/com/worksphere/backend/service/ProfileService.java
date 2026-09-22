package com.worksphere.backend.service;

import com.worksphere.backend.dto.ProfileUpdateRequest;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.entity.User;
import com.worksphere.backend.exception.ResourceNotFoundException;
import com.worksphere.backend.repository.EmployeeRepository;
import com.worksphere.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public ProfileService(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    public Employee getMyProfile(String email) {
        return getEmployeeByEmail(email);
    }

    public Employee updateMyProfile(String email, ProfileUpdateRequest request) {
        Employee employee = getEmployeeByEmail(email);
        employee.setPhone(request.getPhone());
        return employeeRepository.save(employee);
    }

    private Employee getEmployeeByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return employeeRepository.findById(user.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("No employee linked to this account"));
    }
}