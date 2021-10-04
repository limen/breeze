package com.limengxiang.breeze.domain.job;

import com.limengxiang.breeze.domain.executor.model.ExecResult;
import com.limengxiang.breeze.domain.job.service.JobExecLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
public class JobPostExecHandler {

    @Autowired
    JobExecLogService jobExecLogService;

    public void handle(Long jobId, ExecResult result) {
        jobExecLogService.create(jobId, result);
    }
}
