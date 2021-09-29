package com.limengxiang.breeze.model;

import com.limengxiang.breeze.model.dao.JobMapper;
import com.limengxiang.breeze.model.entity.db.JobEntity;
import com.limengxiang.breeze.model.entity.db.JobExecLogEntity;
import com.limengxiang.breeze.model.entity.query.JobStatEntity;
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
public class JobService {

    private JobMapper jobMapper;

    @Autowired
    public JobService(JobMapper jobMapper) {
        this.jobMapper = jobMapper;
    }

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
    public List<Long> findJobIdsInRange(Long jobIdLow, Long jobIdUp, int limit) {
        return jobMapper.queryJobIdsInRange(jobIdLow, jobIdUp, limit);
    }

    /**
     * borders are inclusive
     * @param jobIdLow
     * @param jobIdUp
     * @return
     */
    public Long lastJobIdInRange(Long jobIdLow, Long jobIdUp) {
        return jobMapper.lastJobIdInRange(jobIdLow, jobIdUp);
    }

    public List<JobStatEntity> jobStat(Long jobIdLow, Long jobIdUp, Integer status) {
        return jobMapper.queryJobStat(jobIdLow, jobIdUp, status);
    }

    public JobEntity find(Long jobId) {
        return jobMapper.queryByJobId(jobId);
    }

    public List<JobExecLogEntity> jobLogs(Long jobId) {
        return jobMapper.queryJobLogs(jobId);
    }
}
