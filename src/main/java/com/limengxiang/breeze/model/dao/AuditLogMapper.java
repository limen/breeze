package com.limengxiang.breeze.model.dao;

import com.limengxiang.breeze.http.request.AuditLogQueryEntity;
import com.limengxiang.breeze.model.entity.db.AuditLogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Mapper
public interface AuditLogMapper {

    void insert(AuditLogEntity logEntity);

    List<AuditLogEntity> query(AuditLogQueryEntity queryEntity);

}
