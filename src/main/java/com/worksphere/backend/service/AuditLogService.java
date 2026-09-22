package com.worksphere.backend.service;

import com.worksphere.backend.entity.AuditLog;
import com.worksphere.backend.enums.AuditAction;
import com.worksphere.backend.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // Internal helper: log an action (used by other services)
    public void logAction(AuditAction action, String entityName, String performedBy) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setEntityName(entityName);
        log.setPerformedBy(performedBy);
        log.setTimeStamp(LocalDateTime.now());

        auditLogRepository.save(log);
    }

    // Get all logs (ADMIN only)
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }
}
