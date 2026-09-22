package com.worksphere.backend.dto;

import com.worksphere.backend.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskResponse {

    private Long id;
    private String title;
    private String description;

    private Long assignedToId;
    private String assignedToName;

    private Long assignedById;
    private String assignedByName;

    private LocalDate dueDate;
    private TaskStatus status;
}
