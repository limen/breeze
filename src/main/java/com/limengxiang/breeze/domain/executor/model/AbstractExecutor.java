package com.limengxiang.breeze.domain.executor.model;

import com.limengxiang.breeze.domain.job.model.JobEntity;
import lombok.Data;

import java.util.Map;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
abstract public class AbstractExecutor implements IExecutor {

     protected ExecutorEntity entity;

     public AbstractExecutor() {}

     public AbstractExecutor(ExecutorEntity entity) {
          this.entity = entity;
     }

     abstract protected void parseConfig();

     abstract public ExecResult exec(Map<String, Object> params);
}
