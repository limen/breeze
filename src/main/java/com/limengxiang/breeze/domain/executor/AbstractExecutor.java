package com.limengxiang.breeze.domain.executor;

import com.limengxiang.breeze.model.entity.db.ExecutorEntity;
import com.limengxiang.breeze.model.entity.db.JobEntity;
import lombok.Data;

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

     abstract public ExecResult exec(JobEntity job);
}
