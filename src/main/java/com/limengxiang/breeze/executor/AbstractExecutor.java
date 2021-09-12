package com.limengxiang.breeze.executor;

import com.limengxiang.breeze.model.entity.ExecutorEntity;
import com.limengxiang.breeze.model.entity.JobEntity;
import lombok.Data;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
abstract public class AbstractExecutor {

     protected ExecutorEntity entity;

     public AbstractExecutor() {}

     abstract protected void parseConfig();

     abstract public ExecResult exec(JobEntity job);
}
