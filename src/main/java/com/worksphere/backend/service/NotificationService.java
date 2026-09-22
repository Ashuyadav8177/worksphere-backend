package com.worksphere.backend.service;

import com.worksphere.backend.dto.NotificationResponse;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.entity.Notification;
import com.worksphere.backend.entity.User;
import com.worksphere.backend.exception.ResourceNotFoundException;
import com.worksphere.backend.repository.EmployeeRepository;
import com.worksphere.backend.repository.NotificationRepository;
import com.worksphere.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    public List<NotificationResponse> getMyNotifications(String email) {
        Employee employee = getEmployeeByEmail(email);
        return notificationRepository.findByEmployeeId(employee.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public NotificationResponse markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

        notification.setIsRead(true);
        Notification saved = notificationRepository.save(notification);
        return mapToResponse(saved);
    }

    public void createNotification(Employee employee, String message) {
        Notification notification = new Notification();
        notification.setEmployee(employee);
        notification.setMessage(message);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    private Employee getEmployeeByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return employeeRepository.findById(user.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("No employee linked to this account"));
    }

    private NotificationResponse mapToResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setMessage(notification.getMessage());
        response.setIsRead(notification.getIsRead());
        response.setCreatedAt(notification.getCreatedAt());
        return response;
    }
}