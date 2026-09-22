package com.worksphere.backend.dto;

import com.worksphere.backend.enums.AttendanceStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AttendanceResponse {

    private Long id;
    private Long employeeId;
    private  String employeeName;
    private LocalDate date;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private AttendanceStatus status;


}
