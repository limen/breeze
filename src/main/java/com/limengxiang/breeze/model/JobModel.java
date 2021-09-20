package com.limengxiang.breeze.model;

import com.limengxiang.breeze.model.dao.JobMapper;
import com.limengxiang.breeze.model.entity.JobEntity;
import com.limengxiang.breeze.model.entity.JobExecLogEntity;
import com.limengxiang.breeze.model.entity.JobStatEntity;
import com.limengxiang.breeze.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Service
public class JobModel {

    @Autowired
    private JobMapper jobMapper;

    @Transactional
    public void create(Long jobId, String jobName, Date scheduleAt, Long executorId, Object params) {
        String strParams = params instanceof String ? (String) params : JSONUtil.stringify(params);
        jobMapper.insert(jobId, jobName, scheduleAt, executorId, strParams);
    }

    /**
     * @param jobIdLow inclusive
     * @param jobIdUp inclusive
     * @param limit
     * @return
     */
    public List<String> queryRange(Object jobIdLow, Object jobIdUp, int limit) {
        return jobMapper.queryRange(jobIdLow, jobIdUp, limit);
    }

    /**
     * borders are inclusive
     * @param jobIdLow
     * @param jobIdUp
     * @return
     */
    public Long lastJobIdInRange(Object jobIdLow, Object jobIdUp) {
        return jobMapper.lastJobIdInRange(jobIdLow, jobIdUp);
    }

    public List<JobStatEntity> jobStat(Object jobIdLow, Object jobIdUp, Integer status) {
        return jobMapper.jobStat(jobIdLow, jobIdUp, status);
    }

    public JobEntity jobDetail(Object jobId) {
        return jobMapper.jobDetail(jobId);
    }

    public List<JobExecLogEntity> jobLogs(Object jobId) {
        return jobMapper.jobLogs(jobId);
    }
}
