package com.limengxiang.breeze.model;

import com.limengxiang.breeze.executor.ExecResult;
import com.limengxiang.breeze.model.dao.JobExecLogMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Log
@Service
public class JobExecLogModel {

    @Autowired
    private JobExecLogMapper mapper;

    public void saveLog(Long jobId, ExecResult result) {
        mapper.insert(
                jobId,
                result.getContext(),
                result.getStart(),
                result.getExecStatus().getCode(),
                result.elapse(),
                new Date()
        );
    }

}
