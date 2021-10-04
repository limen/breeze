package com.limengxiang.breeze.domain.executor.model;

import com.limengxiang.breeze.domain.executor.ExecutorPrelude;
import org.springframework.stereotype.Component;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
public class ExecutorFactory {

    public static AbstractExecutor getInstance(ExecutorEntity entity) {
        ExecutorPrelude.ExecutorType executorType = ExecutorPrelude.ExecutorType.valueOf(entity.getType());
        switch (executorType) {
            case http:
                AbstractExecutor executor = new HttpExecutor();
                executor.setEntity(entity);
                executor.parseConfig();
                return executor;
            case stdout:
                return new StdoutExecutor(entity);
            default:
                throw new RuntimeException("Unsupported executor type:" + entity.getType());

        }
    }
}
