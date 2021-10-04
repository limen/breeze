package com.limengxiang.breeze.domain.executor.model;

import com.limengxiang.breeze.domain.executor.ExecutorPrelude;
import com.limengxiang.breeze.utils.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class ExecResult {

    private Date start;
    private Date end;

    private Throwable exception;

    private ExecutorPrelude.ExecStatus execStatus;

    private String context;

    public ExecResult() {
        start = new Date();
    }

    public int elapse() {
        return (int) (DateUtil.toMilliTimestamp(end) - DateUtil.toMilliTimestamp(start));
    }

    public String toString() {
        String value = "Context:" + context + ",Status:" + execStatus.getCode();
        if (exception != null) {
            value += ",Ex:" + exception.getMessage();
        }
        return value;
    }

}
