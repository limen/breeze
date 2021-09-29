package com.limengxiang.breeze.domain.job;

import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.model.JobService;
import com.limengxiang.breeze.model.dao.RedisOps;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class RedisJobIdProvider implements IJobIdProvider {

    private Config config;

    private RedisOps redisOps;

    private JobService jobService;

    public RedisJobIdProvider(RedisOps redisOps, JobService jobService) {
        this.redisOps = redisOps;
        this.jobService = jobService;
    }

    public void setConfig(Config conf) {
        config = conf;
    }

    @Override
    public long nextOnTime(Date time) {
        long tick = getTick(time);
        return nextOnTime(tick);
    }

    @Override
    public long nextOnTime(long time) {
        String key = initIfAbsent(time);
        Long increment = redisOps.increment(key);
        if (increment > JobPrelude.JOB_ID_SEQ_MAX) {
            throw new RuntimeException("Job id overflow #" + time + ", now value " + increment);
        }
        return (time << JobPrelude.JOB_ID_SEQ_WIDTH) | increment;
    }

    @Override
    public long firstOnTime(Date time) {
        long tick = getTick(time);
        return firstOnTime(tick);
    }

    @Override
    public long firstOnTime(long tick) {
        return (tick << JobPrelude.JOB_ID_SEQ_WIDTH) + 1;
    }

    @Override
    public long lastOnTime(Date time) {
        long tick = getTick(time);
        return lastOnTime(tick);
    }

    @Override
    public long lastOnTime(long tick) {
        return tick << JobPrelude.JOB_ID_SEQ_WIDTH | JobPrelude.JOB_ID_SEQ_MAX;
    }

    private String initIfAbsent(long tick) {
        String key = counterKey(tick);
        if (redisOps.getInt(key) == null) {
            Long jobId = jobService.lastJobIdInRange(firstOnTime(tick), lastOnTime(tick));
            redisOps.setIfAbsent(key, jobId == null ? 0 : jobId, 1, TimeUnit.DAYS);
        }
        return key;
    }

    private String counterKey(long tick) {
        return JobPrelude.JOB_ID_SEQ_PREFIX + tick;
    }

    private long getTick(Date time) {
        return time.getTime() / 1000;
    }

}
