package com.limengxiang.breeze.domain.job;

import com.limengxiang.breeze.model.dao.RedisOps;
import com.limengxiang.breeze.utils.NumUtil;

import java.util.List;

public class RedisJobQueue implements IJobQueue {

    private RedisOps redisOps;

    public RedisJobQueue() {}

    public RedisJobQueue(RedisOps redisOps) {
        this.redisOps = redisOps;
    }

    public void setRedisOps(RedisOps redisOps) {
        this.redisOps = redisOps;
    }

    public Long pop() {
        Object p = redisOps.pop(JobPrelude.JOB_QUEUE_KEY);
        return NumUtil.toLong(p);
    }

    @Override
    public long push(List<Long> jobIds) {
        return redisOps.push(JobPrelude.JOB_QUEUE_KEY, jobIds);
    }

    @Override
    public long size() {
        return redisOps.listSize(JobPrelude.JOB_QUEUE_KEY);
    }
}
