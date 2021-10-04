package com.limengxiang.breeze.domain.audit.repository;

import com.limengxiang.breeze.http.request.AuditLogQueryEntity;
import com.limengxiang.breeze.domain.audit.model.AuditLogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Mapper
public interface AuditLogRepository {

    void insert(AuditLogEntity logEntity);

    List<AuditLogEntity> query(AuditLogQueryEntity queryEntity);

}
