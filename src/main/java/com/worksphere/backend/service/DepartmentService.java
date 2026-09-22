package com.worksphere.backend.service;

import com.worksphere.backend.dto.DepartmentRequest;
import com.worksphere.backend.entity.Department;
import com.worksphere.backend.exception.ResourceNotFoundException;
import com.worksphere.backend.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // Create Department
    public Department createDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        department.setDescription(request.getDescription());

        return departmentRepository.save(department);
    }

    // Get All Departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Get Department By Id
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    // Update Department
    public Department updateDepartment(Long id, DepartmentRequest request) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        existingDepartment.setName(request.getName());
        existingDepartment.setDescription(request.getDescription());

        return departmentRepository.save(existingDepartment);
    }

    // Delete Department
    public void deleteDepartment(Long id) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        departmentRepository.delete(existingDepartment);
    }
}
