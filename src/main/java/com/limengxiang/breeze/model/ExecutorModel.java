package com.limengxiang.breeze.model;

import com.limengxiang.breeze.model.dao.ExecutorMapper;
import com.limengxiang.breeze.model.entity.ExecutorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class ExecutorModel {

    @Autowired
    private ExecutorMapper mapper;

    @Transactional
    public void create(ExecutorEntity entity) {
        if (entity.getId() != null) {
            mapper.insert(entity.getId(), entity.getName(), entity.getType(), entity.getConfig(), entity.getParams());
        } else {
            mapper.insertAndGetId(entity);
        }
    }

    public void update(int id, String name, String type, String config, String params) {
        mapper.update(id, name, type, config, params);
    }

    public ExecutorEntity findOne(int id) {
        return mapper.findOne(id);
    }
}
