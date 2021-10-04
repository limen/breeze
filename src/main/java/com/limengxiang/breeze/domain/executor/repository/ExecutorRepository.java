package com.limengxiang.breeze.domain.executor.repository;

import com.limengxiang.breeze.domain.executor.model.ExecutorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Mapper
public interface ExecutorRepository {

    int insert(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("type") String type,
        @Param("config") String config,
        @Param("params") String params
    );

    void insertAndGetId(ExecutorEntity entity);

    void update(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("type") String type,
        @Param("config") String config,
        @Param("params") String params
    );

    ExecutorEntity queryById(@Param("id") int id);

}
