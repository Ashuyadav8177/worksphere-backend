package com.worksphere.backend.dto;

import com.worksphere.backend.entity.Employee;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class NotificationResponse {

    private  Long id;

    private  String message;

    private Boolean isRead;

    private LocalDateTime createdAt;
}
