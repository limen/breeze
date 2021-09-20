package com.limengxiang.breeze.admin.controller;

import com.limengxiang.breeze.admin.aop.AuditPoint;
import com.limengxiang.breeze.admin.aop.AuthPoint;
import com.limengxiang.breeze.consts.ApiConst;
import com.limengxiang.breeze.consts.ExecutorConst;
import com.limengxiang.breeze.httpio.request.JobPostEntity;
import com.limengxiang.breeze.httpio.request.JobStatReqEntity;
import com.limengxiang.breeze.httpio.response.RespEntity;
import com.limengxiang.breeze.httpio.response.RespFactory;
import com.limengxiang.breeze.job.IJobIdManager;
import com.limengxiang.breeze.job.JobIdHelper;
import com.limengxiang.breeze.model.JobModel;
import com.limengxiang.breeze.model.entity.JobStatEntity;
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

    @Autowired
    private JobModel jobModel;

    @Autowired
    private IJobIdManager jobIdManager;

    @AuditPoint
    @PostMapping(ApiConst.URI_JOB_CREATE)
    public RespEntity create(@RequestBody JobPostEntity postEntity) {
        postEntity.validate().throwOnError();
        Date execAt;
        if (StrUtil.notEmpty(postEntity.getExecAt())) {
            execAt = DateUtil.parse(postEntity.getExecAt());
        } else {
            execAt = new Date(System.currentTimeMillis() + (postEntity.getExecAfter() * 1000));
        }
        long jobId = jobIdManager.nextOnTime(execAt);
        jobModel.create(jobId, postEntity.getJobName(), execAt, postEntity.getExecutorId(), postEntity.getParams());
        return RespFactory.success(jobId);
    }

    @AuditPoint
    @PostMapping(ApiConst.URI_JOB_DELETE)
    public RespEntity delete(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobId);
    }

    @AuditPoint
    @PostMapping(ApiConst.URI_JOB_UPDATE)
    public RespEntity update(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobId);
    }

    @AuditPoint
    @PostMapping(ApiConst.URI_JOB_DISABLE)
    public RespEntity disable(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobId);
    }

    @AuditPoint
    @PostMapping(ApiConst.URI_JOB_RECALL)
    public RespEntity recall(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobId);
    }

    @AuthPoint
    @GetMapping(ApiConst.URI_JOB_DETAIL)
    public RespEntity detail(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobModel.jobDetail(Long.valueOf(jobId)));
    }

    @AuthPoint
    @GetMapping(ApiConst.URI_JOB_LOGS)
    public RespEntity logs(@RequestParam("jobId") String jobId) {
        return RespFactory.success(jobModel.jobLogs(Long.valueOf(jobId)));
    }

    @AuthPoint
    @GetMapping(ApiConst.URI_JOB_STAT)
    public RespEntity stat(HttpServletRequest request) {
        JobStatReqEntity entity = new JobStatReqEntity(request);
        entity.validate().throwOnError();

        long jobIdLow = JobIdHelper.firstOnTime(DateUtil.parse(entity.getFromTime()));
        long jobIdUp = JobIdHelper.lastOnTime(DateUtil.parse(entity.getToTime()));

        Map<String, Object> resp = new HashMap<>();
        List<JobStatEntity> data = jobModel.jobStat(jobIdLow, jobIdUp, ExecutorConst.ExecStatus.sync_ok.getCode());

        Map<Long, Integer> jobExecStatus = new HashMap<>();
        int total = 0;
        int failed = 0;
        int missed = 0;
        for (JobStatEntity jobStat : data) {
            if (jobExecStatus.containsKey(jobStat.getJobId())) {
                continue;
            }
            total++;
            jobExecStatus.put(jobStat.getJobId(), jobStat.getExecStatus());
            if (jobStat.getExecStatus() == null) {
                missed++;
            } else if (!ExecutorConst.isOKStatus(jobStat.getExecStatus())) {
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
