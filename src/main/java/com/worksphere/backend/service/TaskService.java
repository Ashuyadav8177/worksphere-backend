package com.worksphere.backend.service;

import com.worksphere.backend.dto.TaskRequest;
import com.worksphere.backend.dto.TaskResponse;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.entity.Task;
import com.worksphere.backend.entity.User;
import com.worksphere.backend.enums.TaskStatus;
import com.worksphere.backend.exception.ResourceNotFoundException;
import com.worksphere.backend.repository.EmployeeRepository;
import com.worksphere.backend.repository.TaskRepository;
import com.worksphere.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    public TaskResponse createTask(String email, TaskRequest request) {
        Employee assignedBy = getEmployeeByEmail(email);
        Employee assignedTo = employeeRepository.findById(request.getAssignedToId())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned employee not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setAssignedTo(assignedTo);
        task.setAssignedBy(assignedBy);
        task.setDueDate(request.getDueDate());
        task.setStatus(TaskStatus.TODO);

        Task saved = taskRepository.save(task);
        return mapToResponse(saved);
    }

    public List<TaskResponse> getMyTasks(String email) {
        Employee employee = getEmployeeByEmail(email);
        return taskRepository.findByAssignedToId(employee.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setStatus(status);
        Task saved = taskRepository.save(task);
        return mapToResponse(saved);
    }

    private Employee getEmployeeByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return employeeRepository.findById(user.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("No employee linked to this account"));
    }
    // Update Task (full edit — ADMIN/MANAGER)
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        Employee assignedTo = employeeRepository.findById(request.getAssignedToId())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned employee not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setAssignedTo(assignedTo);
        task.setDueDate(request.getDueDate());

        Task saved = taskRepository.save(task);
        return mapToResponse(saved);
    }

    // Delete Task (ADMIN/MANAGER)
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        taskRepository.delete(task);
    }

    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setAssignedToId(task.getAssignedTo().getId());
        response.setAssignedToName(task.getAssignedTo().getFirstName() + " " + task.getAssignedTo().getLastName());
        response.setAssignedById(task.getAssignedBy().getId());
        response.setAssignedByName(task.getAssignedBy().getFirstName() + " " + task.getAssignedBy().getLastName());
        response.setDueDate(task.getDueDate());
        response.setStatus(task.getStatus());
        return response;
    }
}