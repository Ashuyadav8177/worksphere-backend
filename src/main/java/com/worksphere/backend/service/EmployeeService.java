package com.worksphere.backend.service;

import com.worksphere.backend.dto.EmployeeRequest;
import com.worksphere.backend.entity.Employee;
import com.worksphere.backend.exception.ResourceNotFoundException;
import com.worksphere.backend.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Create Employee
    public Employee createEmployee(EmployeeRequest request) {
        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());

        return employeeRepository.save(employee);
    }

    // Get All Active Employees (Paginated)
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findByActiveTrue(pageable);
    }

    // Get Active Employee By Id
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));
    }

    // Search Active Employees by Department
    public List<Employee> searchByDepartment(String department) {
        return employeeRepository.findByDepartmentAndActiveTrue(department);
    }

    // Update Active Employee
    public Employee updateEmployee(Long id, EmployeeRequest request) {
        Employee existingEmployee = employeeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));

        existingEmployee.setFirstName(request.getFirstName());
        existingEmployee.setLastName(request.getLastName());
        existingEmployee.setEmail(request.getEmail());
        existingEmployee.setPhone(request.getPhone());
        existingEmployee.setDepartment(request.getDepartment());
        existingEmployee.setDesignation(request.getDesignation());
        existingEmployee.setSalary(request.getSalary());

        return employeeRepository.save(existingEmployee);
    }

    // Delete Employee - Soft Delete
    public void deleteEmployee(Long id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));

        existingEmployee.setActive(false);

        employeeRepository.save(existingEmployee);
    }
}