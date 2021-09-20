package com.limengxiang.breeze.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Mapper
public interface JobExecLogMapper {

    void insert(@Param("jobId") Long jobId,
                @Param("context") String context,
                @Param("execAt") Date execAt,
                @Param("status") int status,
                @Param("elapse") Integer elapse,
                @Param("createdAt") Date createdAt
                );

}
