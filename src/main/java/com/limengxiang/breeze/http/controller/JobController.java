package com.limengxiang.breeze.http.controller;

import com.limengxiang.breeze.http.aop.AuditPoint;
import com.limengxiang.breeze.http.aop.AuthPoint;
import com.limengxiang.breeze.http.HttpPrelude;
import com.limengxiang.breeze.domain.executor.ExecutorPrelude;
import com.limengxiang.breeze.http.request.JobPostEntity;
import com.limengxiang.breeze.http.request.JobStatQueryEntity;
import com.limengxiang.breeze.http.response.RespEntity;
import com.limengxiang.breeze.http.response.RespFactory;
import com.limengxiang.breeze.domain.job.model.IJobIdProvider;
import com.limengxiang.breeze.domain.job.JobIdHelper;
import com.limengxiang.breeze.domain.job.service.JobService;
import com.limengxiang.breeze.domain.job.model.JobStatValue;
import com.limengxiang.breeze.utils.DateUtil;
import com.limengxiang.breeze.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@RestController
public class JobController {

    private JobService jobService;

    private IJobIdProvider jobIdProvider;

    @Autowired
    public JobController(JobService job, IJobIdProvider jobIdProvider) {
        this.jobService = job;
        this.jobIdProvider = jobIdProvider;
    }

    @AuditPoint
    @PostMapping(HttpPrelude.URI_JOB_CREATE)
    public RespEntity create(@RequestBody JobPostEntity postEntity) {
        postEntity.validate().throwOnError();
        Date execAt;
        if (StrUtil.notEmpty(postEntity.getExecAt())) {
            execAt = DateUtil.parse(postEntity.getExecAt());
        } else {
            execAt = new Date(System.currentTimeMillis() + (postEntity.getExecAfter() * 1000));
        }
        long jobId = jobIdProvider.nextOnTime(execAt);
        jobService.create(jobId, postEntity.getJobName(), execAt, postEntity.getExecutorId(), postEntity.getParams());
        return RespFactory.success(jobId);
    }

    @AuditPoint
    @PostMapping(HttpPrelude.URI_JOB_DELETE)
    public RespEntity delete(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobId);
    }

    @AuditPoint
    @PostMapping(HttpPrelude.URI_JOB_UPDATE)
    public RespEntity update(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobId);
    }

    @AuditPoint
    @PostMapping(HttpPrelude.URI_JOB_DISABLE)
    public RespEntity disable(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobId);
    }

    @AuditPoint
    @PostMapping(HttpPrelude.URI_JOB_RECALL)
    public RespEntity recall(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobId);
    }

    @AuthPoint
    @GetMapping(HttpPrelude.URI_JOB_DETAIL)
    public RespEntity detail(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobService.find(Long.valueOf(jobId)));
    }

    @AuthPoint
    @GetMapping(HttpPrelude.URI_JOB_LOGS)
    public RespEntity logs(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobService.jobLogs(Long.valueOf(jobId)));
    }

    @AuthPoint
    @GetMapping(HttpPrelude.URI_JOB_STAT)
    public RespEntity stat(HttpServletRequest request) {
        JobStatQueryEntity entity = new JobStatQueryEntity(request);
        entity.validate().throwOnError();

        long jobIdLow = JobIdHelper.firstOnTime(DateUtil.parse(entity.getFromTime()));
        long jobIdUp = JobIdHelper.lastOnTime(DateUtil.parse(entity.getToTime()));

        Map<String, Object> resp = new HashMap<>();
        List<JobStatValue> data = jobService.jobStat(jobIdLow, jobIdUp, ExecutorPrelude.ExecStatus.sync_ok.getCode());

        Map<Long, Integer> jobExecStatus = new HashMap<>();
        int total = 0;
        int failed = 0;
        int missed = 0;
        for (JobStatValue jobStat : data) {
            if (jobExecStatus.containsKey(jobStat.getJobId())) {
                continue;
            }
            total++;
            jobExecStatus.put(jobStat.getJobId(), jobStat.getExecStatus());
            if (jobStat.getExecStatus() == null) {
                missed++;
            } else if (!ExecutorPrelude.isOKStatus(jobStat.getExecStatus())) {
                failed++;
            }
        }

        resp.put("total", total);
        resp.put("failed", failed);
        resp.put("missed", missed);
        resp.put("jobExecStatus", jobExecStatus);

        return RespFactory.success(resp);
    }

}
