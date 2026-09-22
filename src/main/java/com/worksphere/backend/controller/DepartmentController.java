package com.worksphere.backend.controller;

import com.worksphere.backend.dto.DepartmentRequest;
import com.worksphere.backend.entity.Department;
import com.worksphere.backend.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private  final  DepartmentService departmentService;

    public  DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    // CREATE DEPARTMENT
    @PreAuthorize("hasAnyRole('ADMIN' , 'MANAGER')")
    @PostMapping
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody DepartmentRequest request){
        Department savedDepartment = departmentService.createDepartment(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }
    // GET DEPARTMENT
    @GetMapping
    public  ResponseEntity<List<Department>> getAllDepartments(){
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }
    // GET DEPARTMENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    // Update Department
    @PreAuthorize("hasRoleAny('ADMIN' , 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {
        Department updatedDepartment = departmentService.updateDepartment(id, request);
        return ResponseEntity.ok(updatedDepartment);
    }

    // Delete Department
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

}
