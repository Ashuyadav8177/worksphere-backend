package com.worksphere.backend.controller;

import com.worksphere.backend.dto.TaskRequest;
import com.worksphere.backend.dto.TaskResponse;
import com.worksphere.backend.enums.TaskStatus;
import com.worksphere.backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Create Task (ADMIN/MANAGER only)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(Authentication authentication, @Valid @RequestBody TaskRequest request) {
        String email = authentication.getName();
        TaskResponse response = taskService.createTask(email, request);
        return ResponseEntity.ok(response);
    }

    // Get My Tasks
    @GetMapping("/my")
    public ResponseEntity<List<TaskResponse>> getMyTasks(Authentication authentication) {
        String email = authentication.getName();
        List<TaskResponse> response = taskService.getMyTasks(email);
        return ResponseEntity.ok(response);
    }

    // Get All Tasks (ADMIN/MANAGER only)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> response = taskService.getAllTasks();
        return ResponseEntity.ok(response);
    }

    // Update Task Status
    @PutMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        TaskResponse response = taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok(response);
    }
    // Update Task
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.updateTask(id, request);
        return ResponseEntity.ok(response);
    }

    // Delete Task
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
