package com.limengxiang.breeze.domain.executor;

import com.limengxiang.breeze.model.entity.db.ExecutorEntity;
import com.limengxiang.breeze.model.entity.db.JobEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class StdoutExecutor extends AbstractExecutor {

    public StdoutExecutor(ExecutorEntity entity) {
        this.entity = entity;
    }

    @Override
    protected void parseConfig() {

    }

    @Override
    public ExecResult exec(JobEntity job) {
        ExecResult result = new ExecResult();
        result.setContext("Stdout: job id:" + job.getJobId() + ",params:" + job.getJobParams());
        result.setExecStatus(ExecutorPrelude.ExecStatus.sync_ok);
        result.setEnd(new Date());
        return result;
    }
}
