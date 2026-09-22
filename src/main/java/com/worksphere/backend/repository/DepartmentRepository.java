package com.worksphere.backend.repository;

import com.worksphere.backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByNameContainingIgnoreCase(String name);
}