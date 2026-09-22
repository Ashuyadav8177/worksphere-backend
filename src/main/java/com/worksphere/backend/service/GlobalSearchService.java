package com.worksphere.backend.service;

import com.worksphere.backend.dto.GlobalSearchResponse;
import com.worksphere.backend.entity.Department;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.entity.Leave;
import com.worksphere.backend.entity.Task;
import com.worksphere.backend.repository.DepartmentRepository;
import com.worksphere.backend.repository.EmployeeRepository;
import com.worksphere.backend.repository.LeaveRepository;
import com.worksphere.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalSearchService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final TaskRepository taskRepository;
    private final LeaveRepository leaveRepository;

    public GlobalSearchService(EmployeeRepository employeeRepository,
                               DepartmentRepository departmentRepository,
                               TaskRepository taskRepository,
                               LeaveRepository leaveRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.taskRepository = taskRepository;
        this.leaveRepository = leaveRepository;
    }

    public GlobalSearchResponse search(String query) {

        GlobalSearchResponse response = new GlobalSearchResponse();

        // Employee search
        List<Employee> employees =
                employeeRepository
                        .findByActiveTrueAndFirstNameContainingIgnoreCaseOrActiveTrueAndLastNameContainingIgnoreCase(
                                query,
                                query
                        );

        List<GlobalSearchResponse.SearchResult> employeeResults =
                employees.stream()
                        .map(employee -> new GlobalSearchResponse.SearchResult(
                                employee.getId(),
                                employee.getFirstName() + " " + employee.getLastName(),
                                employee.getEmail(),
                                "EMPLOYEE"
                        ))
                        .toList();

        response.setEmployees(employeeResults);

        // Department search
        List<Department> departments =
                departmentRepository.findByNameContainingIgnoreCase(query);

        List<GlobalSearchResponse.SearchResult> departmentResults =
                departments.stream()
                        .map(department -> new GlobalSearchResponse.SearchResult(
                                department.getId(),
                                department.getName(),
                                department.getDescription(),
                                "DEPARTMENT"
                        ))
                        .toList();

        response.setDepartments(departmentResults);

        // Task search
        List<Task> tasks =
                taskRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        query,
                        query
                );

        List<GlobalSearchResponse.SearchResult> taskResults =
                tasks.stream()
                        .map(task -> new GlobalSearchResponse.SearchResult(
                                task.getId(),
                                task.getTitle(),
                                task.getDescription(),
                                "TASK"
                        ))
                        .toList();

        response.setTasks(taskResults);

        // Leave search
        List<Leave> leaves =
                leaveRepository.findByReasonContainingIgnoreCase(query);

        List<GlobalSearchResponse.SearchResult> leaveResults =
                leaves.stream()
                        .map(leave -> new GlobalSearchResponse.SearchResult(
                                leave.getId(),
                                leave.getReason(),
                                leave.getStatus().name(),
                                "LEAVE"
                        ))
                        .toList();

        response.setLeaves(leaveResults);

        return response;
    }
}