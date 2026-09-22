package com.worksphere.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardResponse {

    private long totalEmployees;
    private long totalDepartments;
    private long pendingLeaves;
    private long totalTasks;
    private long completedTasks;
}