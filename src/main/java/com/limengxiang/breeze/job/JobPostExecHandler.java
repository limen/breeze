package com.limengxiang.breeze.job;

import com.limengxiang.breeze.executor.ExecResult;
import com.limengxiang.breeze.model.JobExecLogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
public class JobPostExecHandler {

    @Autowired
    JobExecLogModel jobExecLogModel;

    public void handle(Object jobId, ExecResult result) {
        jobExecLogModel.saveLog(jobId, result);
    }
}
