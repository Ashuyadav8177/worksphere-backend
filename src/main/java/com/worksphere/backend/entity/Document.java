package com.worksphere.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private  Employee employee;

    private  String documentName;

    private  String documentType;

    private String fileUrl;
}
