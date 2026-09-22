package com.worksphere.backend.entity;

import com.worksphere.backend.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long employeeId;

    // No-argument constructor
    public User() {
    }

    // Parameterized constructor
    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
