package com.limengxiang.breeze.domain.job;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JdkJobQueue implements IJobQueue {

    private LinkedBlockingQueue<Long> queue;

    public JdkJobQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    @Override
    public long push(List<Long> jobIds) {
        queue.addAll(jobIds);
        return queue.size();
    }

    @Override
    public Long pop() {
        return queue.poll();
    }

    @Override
    public long size() {
        return queue.size();
    }
}
