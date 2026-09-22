package com.worksphere.backend.dto;

import com.worksphere.backend.enums.LeaveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LeaveRequest {

    @NotNull(message = "Start date is mandatory")
    private LocalDate startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDate endDate;

    @NotBlank(message = "Reason is required")
    private String reason;
    @NotNull(message = "Leave type is required")
    private LeaveType leaveType;
}
