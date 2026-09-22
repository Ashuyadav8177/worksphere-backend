package com.worksphere.backend.entity;

import com.worksphere.backend.enums.AuditAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuditAction action;

    private String entityName;
    private String performedBy;
    private LocalDateTime timeStamp;
}
