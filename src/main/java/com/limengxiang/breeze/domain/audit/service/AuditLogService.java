package com.limengxiang.breeze.domain.audit.service;

import com.limengxiang.breeze.http.request.AuditLogQueryEntity;
import com.limengxiang.breeze.domain.audit.repository.AuditLogRepository;
import com.limengxiang.breeze.domain.audit.model.AuditLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class AuditLogService {

    private AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepo) {
        this.auditLogRepository = auditLogRepo;
    }

    @Transactional
    public void create(AuditLogEntity logEntity) {
        auditLogRepository.insert(logEntity);
    }

    public List<AuditLogEntity> find(AuditLogQueryEntity queryEntity) {
        return auditLogRepository.query(queryEntity);
    }

}
