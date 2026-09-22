package com.worksphere.backend.repository;

import com.worksphere.backend.entity.Task;
import com.worksphere.backend.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedToId(Long employeeId);

    long countByStatus(TaskStatus status);

    List<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title,
            String description
    );
}
