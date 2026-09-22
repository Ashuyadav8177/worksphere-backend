package com.worksphere.backend.dto;

import com.worksphere.backend.enums.LeaveStatus;
import com.worksphere.backend.enums.LeaveType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class LeaveResponse {

    private Long id;
    private Long employeeId;
    private String  employeeName;
    private LocalDate startDate;
    private  LocalDate endDate;
    private  String reason;
    private LeaveType leaveType;
    private LeaveStatus status;
}
