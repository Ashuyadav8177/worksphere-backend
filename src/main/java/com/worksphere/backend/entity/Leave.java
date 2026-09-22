package com.worksphere.backend.entity;

import com.worksphere.backend.enums.LeaveStatus;
import com.worksphere.backend.enums.LeaveType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "leaves")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;
}
