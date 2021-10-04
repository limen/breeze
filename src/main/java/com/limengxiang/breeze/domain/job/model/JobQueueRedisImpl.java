package com.limengxiang.breeze.domain.job.model;

import com.limengxiang.breeze.domain.job.JobPrelude;
import com.limengxiang.breeze.redis.RedisOps;
import com.limengxiang.breeze.utils.NumUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class JobQueueRedisImpl implements IJobQueue {

    private RedisOps redisOps;

    private final int capacity;

    public JobQueueRedisImpl(RedisOps redisOps, int capacity) {
        this.capacity = capacity;
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

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isFull() {
        return size() >= capacity;
    }

    @Override
    public void awaitNotEmpty() {
        if (size() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void awaitNotFull() {
        if (size() >= capacity) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
