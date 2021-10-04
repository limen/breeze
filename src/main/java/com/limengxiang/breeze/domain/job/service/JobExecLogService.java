package com.limengxiang.breeze.domain.job.service;

import com.limengxiang.breeze.domain.executor.model.ExecResult;
import com.limengxiang.breeze.domain.job.repository.JobExecLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class JobExecLogService {

    private JobExecLogRepository jobExecLogMapper;

    @Autowired
    public JobExecLogService(JobExecLogRepository jobExecLogMapper) {
        this.jobExecLogMapper = jobExecLogMapper;
    }

    public void create(Long jobId, ExecResult result) {
        jobExecLogMapper.insert(
                jobId,
                result.getContext(),
                result.getStart(),
                result.getExecStatus().getCode(),
                result.elapse(),
                new Date()
        );
    }

}
