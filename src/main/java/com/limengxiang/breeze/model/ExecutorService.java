package com.limengxiang.breeze.model;

import com.limengxiang.breeze.model.dao.ExecutorMapper;
import com.limengxiang.breeze.model.entity.db.ExecutorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class ExecutorService {

    private ExecutorMapper executorMapper;

    @Autowired
    public ExecutorService(ExecutorMapper executorMapper) {
        this.executorMapper = executorMapper;
    }

    @Transactional
    public void create(ExecutorEntity entity) {
        if (entity.getId() != null) {
            executorMapper.insert(entity.getId(), entity.getName(), entity.getType(), entity.getConfig(), entity.getParams());
        } else {
            executorMapper.insertAndGetId(entity);
        }
    }

    public void update(int id, String name, String type, String config, String params) {
        executorMapper.update(id, name, type, config, params);
    }

    public ExecutorEntity find(int id) {
        return executorMapper.queryById(id);
    }
}
