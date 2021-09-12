package com.limengxiang.breeze.job;

import com.limengxiang.breeze.consts.UtilConst;
import com.limengxiang.breeze.model.dao.RedisOps;

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

    public Object pop() {
        return redisOps.pop(UtilConst.JOB_QUEUE_KEY);
    }

    @Override
    public long push(List jobIds) {
        return redisOps.push(UtilConst.JOB_QUEUE_KEY, jobIds);
    }

    @Override
    public long size() {
        return redisOps.listSize(UtilConst.JOB_QUEUE_KEY);
    }
}
