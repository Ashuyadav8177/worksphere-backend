package com.worksphere.backend.repository;

import com.worksphere.backend.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartmentAndActiveTrue(String department);

    Optional<Employee> findByEmail(String email);

    Page<Employee> findByActiveTrue(Pageable pageable);

    long countByActiveTrue();

    Optional<Employee> findByIdAndActiveTrue(Long id);

    List<Employee> findByActiveTrueAndFirstNameContainingIgnoreCaseOrActiveTrueAndLastNameContainingIgnoreCase(
            String firstName,
            String lastName
    );
}
