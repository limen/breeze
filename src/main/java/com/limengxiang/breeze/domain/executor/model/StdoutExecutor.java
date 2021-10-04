package com.limengxiang.breeze.domain.executor.model;

import com.limengxiang.breeze.domain.executor.ExecutorPrelude;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Slf4j
public class StdoutExecutor extends AbstractExecutor {

    public StdoutExecutor(ExecutorEntity entity) {
        this.entity = entity;
    }

    @Override
    protected void parseConfig() {

    }

    @Override
    public ExecResult exec(Map<String, Object> params) {
        ExecResult result = new ExecResult();
        result.setContext("Stdout,params:" + params);
        result.setExecStatus(ExecutorPrelude.ExecStatus.sync_ok);
        result.setEnd(new Date());
        return result;
    }
}
