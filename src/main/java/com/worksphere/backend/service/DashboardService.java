package com.worksphere.backend.service;

import com.worksphere.backend.dto.DashboardResponse;
import com.worksphere.backend.enums.LeaveStatus;
import com.worksphere.backend.enums.TaskStatus;
import com.worksphere.backend.repository.DepartmentRepository;
import com.worksphere.backend.repository.EmployeeRepository;
import com.worksphere.backend.repository.LeaveRepository;
import com.worksphere.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LeaveRepository leaveRepository;
    private final TaskRepository taskRepository;

    public DashboardService(EmployeeRepository employeeRepository,
                            DepartmentRepository departmentRepository,
                            LeaveRepository leaveRepository,
                            TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.leaveRepository = leaveRepository;
        this.taskRepository = taskRepository;
    }

    public DashboardResponse getSummary() {

        DashboardResponse response = new DashboardResponse();

        response.setTotalEmployees(
                employeeRepository.countByActiveTrue()
        );

        response.setTotalDepartments(
                departmentRepository.count()
        );

        response.setPendingLeaves(
                leaveRepository.countByStatus(LeaveStatus.PENDING)
        );

        response.setTotalTasks(
                taskRepository.count()
        );

        response.setCompletedTasks(
                taskRepository.countByStatus(TaskStatus.DONE)
        );

        return response;
    }
}