package com.limengxiang.breeze.job;

import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.consts.UtilConst;
import com.limengxiang.breeze.model.JobModel;
import com.limengxiang.breeze.model.dao.RedisOps;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class RedisJobIdManager implements IJobIdManager {

    private Config config;

    private RedisOps redisOps;

    private JobModel jobModel;

    public RedisJobIdManager(RedisOps redisOps, JobModel jobModel) {
        this.redisOps = redisOps;
        this.jobModel = jobModel;
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
        if (increment > UtilConst.JOB_ID_SEQ_MAX) {
            throw new RuntimeException("Job id overflow #" + time + ", now value " + increment);
        }
        return (time << UtilConst.JOB_ID_SEQ_WIDTH) | increment;
    }

    @Override
    public long firstOnTime(Date time) {
        long tick = getTick(time);
        return firstOnTime(tick);
    }

    @Override
    public long firstOnTime(long tick) {
        return (tick << UtilConst.JOB_ID_SEQ_WIDTH) + 1;
    }

    @Override
    public long lastOnTime(Date time) {
        long tick = getTick(time);
        return lastOnTime(tick);
    }

    @Override
    public long lastOnTime(long tick) {
        return tick << UtilConst.JOB_ID_SEQ_WIDTH | UtilConst.JOB_ID_SEQ_MAX;
    }

    private String initIfAbsent(long tick) {
        String key = counterKey(tick);
        if (redisOps.getInt(key) == null) {
            Long jobId = jobModel.lastJobIdInRange(firstOnTime(tick), lastOnTime(tick));
            redisOps.setIfAbsent(key, jobId == null ? 0 : jobId, 1, TimeUnit.DAYS);
        }
        return key;
    }

    private String counterKey(long tick) {
        return UtilConst.JOB_ID_SEQ_PREFIX + tick;
    }

    private long getTick(Date time) {
        return time.getTime() / 1000;
    }

}
