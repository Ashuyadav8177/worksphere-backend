package com.worksphere.backend.service;

import com.worksphere.backend.dto.AuthResponse;
import com.worksphere.backend.dto.LoginRequest;
import com.worksphere.backend.dto.RegisterRequest;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.entity.User;
import com.worksphere.backend.enums.Role;
import com.worksphere.backend.exception.BadRequestException;
import com.worksphere.backend.exception.ResourceNotFoundException;
import com.worksphere.backend.repository.EmployeeRepository;
import com.worksphere.backend.repository.UserRepository;
import com.worksphere.backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, EmployeeRepository employeeRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Register
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (userRepository.findByEmployeeId(request.getEmployeeId()).isPresent()) {
            throw new BadRequestException("This employee is already linked to an account");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.EMPLOYEE);
        user.setEmployeeId(request.getEmployeeId());

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        return new AuthResponse(token, user.getEmail(), user.getRole().name(), fullName);
    }

    // Login
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        String fullName = "User";
        if (user.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(user.getEmployeeId()).orElse(null);
            if (employee != null) {
                fullName = employee.getFirstName() + " " + employee.getLastName();
            }
        }

        return new AuthResponse(token, user.getEmail(), user.getRole().name(), fullName);
    }
}