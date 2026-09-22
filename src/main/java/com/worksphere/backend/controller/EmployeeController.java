package com.worksphere.backend.controller;

import com.worksphere.backend.dto.EmployeeRequest;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Create Employee
    @PreAuthorize("hasAnyRole('ADMIN' , 'MANAGER')")
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        Employee savedEmployee = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    // Get All Employees (Paginated)
    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<Employee> employees = employeeService.getAllEmployees(pageable);
        return ResponseEntity.ok(employees);
    }

    // Get Employee By Id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    // Update Employee
    @PreAuthorize("hasAnyRole('ADMIN' , 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) {
        Employee updatedEmployee = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(updatedEmployee);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchByDepartment(@RequestParam String department) {
        List<Employee> employees = employeeService.searchByDepartment(department);
        return ResponseEntity.ok(employees);
    }

    // Delete Employee
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
