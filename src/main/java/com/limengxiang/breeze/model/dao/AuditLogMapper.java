package com.limengxiang.breeze.model.dao;

import com.limengxiang.breeze.httpio.request.AuditLogQueryEntity;
import com.limengxiang.breeze.model.entity.AuditLogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Mapper
public interface AuditLogMapper {

    void insert(AuditLogEntity logEntity);

    List<AuditLogEntity> find(AuditLogQueryEntity queryEntity);

}
