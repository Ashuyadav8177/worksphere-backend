package com.worksphere.backend.entity;

import com.worksphere.backend.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate date;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    public  Attendance(){

    }
}
