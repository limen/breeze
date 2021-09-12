package com.limengxiang.breeze.job;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JdkJobQueue implements IJobQueue {

    private LinkedBlockingQueue<Object> queue;

    public JdkJobQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    @Override
    public long push(List jobIds) {
        queue.addAll(jobIds);
        return queue.size();
    }

    @Override
    public Object pop() {
        return queue.poll();
    }

    @Override
    public long size() {
        return queue.size();
    }
}
