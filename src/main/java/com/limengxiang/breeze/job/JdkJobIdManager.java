package com.limengxiang.breeze.job;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.consts.UtilConst;
import com.limengxiang.breeze.model.JobModel;
import lombok.Data;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class JdkJobIdManager implements IJobIdManager {

    private JobModel jobModel;

    private Config config;

    private LoadingCache<Long, AtomicLong> tickCounters = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build(
                    new CacheLoader<Long, AtomicLong>() {
                        @Override
                        public AtomicLong load(Long tick) throws Exception {
                            if (jobModel == null) {
                                return new AtomicLong(0L);
                            }
                            Long jobId = jobModel.lastJobIdInRange(firstOnTime(tick), lastOnTime(tick));
                            return jobId == null ? new AtomicLong(0L) : new AtomicLong(jobId);
                        }
                    }
            );

    public JdkJobIdManager(JobModel jobModel) {
        this.jobModel = jobModel;
    }

    @Override
    public long nextOnTime(Date time) {
        long tick = JobIdHelper.getTick(time);
        return nextOnTime(tick);
    }

    @Override
    public long nextOnTime(long time) {
        long id;
        try {
            id = tickCounters.get(time).addAndGet(1);
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("Get next job id error: " + e.getMessage());
        }
        if (id > UtilConst.JOB_ID_SEQ_MAX) {
            throw new RuntimeException("Job id overflow #" + time + ", now value " + id);
        }
        return (time << UtilConst.JOB_ID_SEQ_WIDTH) | id;
    }

    @Override
    public long firstOnTime(Date time) {
        return JobIdHelper.firstOnTime(time);
    }

    @Override
    public long firstOnTime(long time) {
        return JobIdHelper.firstOnTime(time);
    }

    @Override
    public long lastOnTime(Date time) {
        return JobIdHelper.lastOnTime(time);
    }

    @Override
    public long lastOnTime(long time) {
        return JobIdHelper.lastOnTime(time);
    }
}