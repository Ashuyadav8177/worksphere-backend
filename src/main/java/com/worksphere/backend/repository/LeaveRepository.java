package com.worksphere.backend.repository;

import com.worksphere.backend.entity.Leave;
import com.worksphere.backend.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeId(Long employeeId);

    long countByStatus(LeaveStatus status);

    List<Leave> findByReasonContainingIgnoreCase(String reason);
}