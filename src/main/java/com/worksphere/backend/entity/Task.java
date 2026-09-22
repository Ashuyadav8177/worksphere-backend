package com.worksphere.backend.entity;

import com.worksphere.backend.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private Employee assignedTo;

    @ManyToOne
    @JoinColumn(name = "assigned_by_id")
    private Employee assignedBy;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
