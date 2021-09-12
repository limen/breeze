package com.limengxiang.breeze.executor;

import com.limengxiang.breeze.consts.ExecutorConst;
import com.limengxiang.breeze.model.entity.ExecutorEntity;
import org.springframework.stereotype.Component;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
public class ExecutorFactory {

    public static AbstractExecutor getInstance(ExecutorEntity entity) {
        ExecutorConst.ExecutorType executorType = ExecutorConst.ExecutorType.valueOf(entity.getType());
        switch (executorType) {
            case http:
            case https:
                AbstractExecutor executor = new HttpExecutor();
                executor.setEntity(entity);
                executor.parseConfig();
                return executor;
            case stdout:
                return new StdoutExecutor();
            default:
                throw new RuntimeException("Unsupported executor type:" + entity.getType());

        }
    }
}
