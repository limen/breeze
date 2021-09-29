package com.limengxiang.breeze.model.dao;

import com.limengxiang.breeze.model.entity.db.ExecutorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Mapper
public interface ExecutorMapper {

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
