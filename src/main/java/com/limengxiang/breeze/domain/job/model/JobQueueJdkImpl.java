package com.limengxiang.breeze.domain.job.model;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JobQueueJdkImpl implements IJobQueue {

    private LinkedBlockingQueue<Long> queue;

    private final int capacity;

    private final ReentrantLock fullLock = new ReentrantLock();
    private final Condition notFull = fullLock.newCondition();

    private final ReentrantLock emptyLock = new ReentrantLock();
    private final Condition notEmpty = emptyLock.newCondition();

    public JobQueueJdkImpl() {
        capacity = 1000;
        queue = new LinkedBlockingQueue<>();
    }

    public JobQueueJdkImpl(int capacity) {
        this.capacity = capacity;
        queue = new LinkedBlockingQueue<>();
    }

    @Override
    public long push(List<Long> jobIds) {
        try {
            emptyLock.lock();
            if (queue.addAll(jobIds)) {
                notEmpty.signalAll();
            }
            return queue.size();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            emptyLock.unlock();
        }
        return 0L;
    }

    @Override
    public Long pop() {
        try {
            fullLock.lock();
            if (queue.size() < capacity) {
                notFull.signalAll();
            }
            return queue.poll();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            fullLock.unlock();
        }
        return null;
    }

    @Override
    public long size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean isFull() {
        return size() >= capacity;
    }

    @Override
    public void awaitNotEmpty() {
        try {
            emptyLock.lock();
            if (queue.isEmpty()) {
                notEmpty.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            emptyLock.unlock();
        }
    }

    @Override
    public void awaitNotFull() {
        try {
            fullLock.lock();
            if (queue.size() >= capacity) {
                notFull.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            fullLock.unlock();
        }
    }


}
