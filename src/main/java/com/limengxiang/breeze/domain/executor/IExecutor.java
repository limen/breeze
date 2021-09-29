package com.limengxiang.breeze.domain.executor;

import com.limengxiang.breeze.model.entity.db.JobEntity;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public interface IExecutor {

    ExecResult exec(JobEntity jobEntity);

}
