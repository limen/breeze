package com.limengxiang.breeze.domain.executor.service;

import com.limengxiang.breeze.domain.executor.repository.ExecutorRepository;
import com.limengxiang.breeze.domain.executor.model.ExecutorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class ExecutorService {

    private ExecutorRepository executorMapper;

    @Autowired
    public ExecutorService(ExecutorRepository executorMapper) {
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
