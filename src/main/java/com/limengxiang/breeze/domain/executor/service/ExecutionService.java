package com.limengxiang.breeze.domain.executor.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.domain.executor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class ExecutionService {

    private final ExecutorService executorService;

    private final LoadingCache<Integer, AbstractExecutor> cache;

    @Autowired
    public ExecutionService(Config config, ExecutorService executor) {
        this.executorService = executor;
        cache = CacheBuilder.newBuilder()
                .maximumSize(config.getExecutorCacheCapacity())
                .expireAfterWrite(config.getExecutorCacheTime(), TimeUnit.SECONDS)
                .build(
                        new CacheLoader<Integer, AbstractExecutor>() {
                            @Override
                            public AbstractExecutor load(Integer id) throws Exception {
                                ExecutorEntity entity = executorService.find(id);
                                if (entity != null) {
                                    return ExecutorFactory.getInstance(entity);
                                }
                                return null;
                            }
                        }
                );
    }

    public ExecResult execute(Integer id, Map<String, Object> params) {
        return getExecutor(id).exec(params);
    }

    protected IExecutor getExecutor(Integer id) {
        try {
            return cache.get(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
