package com.limengxiang.breeze.model;

import com.limengxiang.breeze.http.request.AuditLogQueryEntity;
import com.limengxiang.breeze.model.dao.AuditLogMapper;
import com.limengxiang.breeze.model.entity.db.AuditLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class AuditLogService {

    private AuditLogMapper auditLogMapper;

    @Autowired
    public AuditLogService(AuditLogMapper auditLogMapper) {
        this.auditLogMapper = auditLogMapper;
    }

    @Transactional
    public void create(AuditLogEntity logEntity) {
        auditLogMapper.insert(logEntity);
    }

    public List<AuditLogEntity> find(AuditLogQueryEntity queryEntity) {
        return auditLogMapper.query(queryEntity);
    }

}
