package com.limengxiang.breeze.manager;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.executor.AbstractExecutor;
import com.limengxiang.breeze.executor.ExecutorFactory;
import com.limengxiang.breeze.model.ExecutorModel;
import com.limengxiang.breeze.model.entity.ExecutorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
public class ExecutorManager {

    @Autowired
    private Config config;

    @Autowired
    private ExecutorModel executorModel;

    private final LoadingCache<Integer, AbstractExecutor> cache = CacheBuilder.newBuilder()
            .maximumSize(config.getExecutorCacheCapacity())
            .expireAfterWrite(config.getExecutorCacheTime(), TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Integer, AbstractExecutor>() {
                        @Override
                        public AbstractExecutor load(Integer id) throws Exception {
                            ExecutorEntity entity = executorModel.findOne(id);
                            if (entity != null) {
                                return ExecutorFactory.getInstance(entity);
                            }
                            return null;
                        }
                    }
            );

    public AbstractExecutor getExecutor(Integer id) {
        try {
            return cache.get(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
