package com.limengxiang.breeze.model;

import com.limengxiang.breeze.httpio.request.AuditLogQueryEntity;
import com.limengxiang.breeze.model.dao.AuditLogMapper;
import com.limengxiang.breeze.model.entity.AuditLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class AuditLogModel {

    @Autowired
    private AuditLogMapper mapper;

    @Transactional
    public void insert(AuditLogEntity logEntity) {
        mapper.insert(logEntity);
    }

    public List<AuditLogEntity> find(AuditLogQueryEntity queryEntity) {
        return mapper.find(queryEntity);
    }

}
