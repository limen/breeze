package com.limengxiang.breeze.concurrent;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class NamedThreadFactory implements ThreadFactory {

    private final String id;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public NamedThreadFactory(String id) {
        this.id = id;
    }

    @Override
    public Thread newThread(Runnable target) {
        int threadSeq = threadNumber.getAndIncrement();
        if (threadSeq == Integer.MAX_VALUE) {
            threadNumber.set(1);
            threadSeq = threadNumber.getAndIncrement();
        }
        String name = id + "-" + threadSeq;
        return new Thread(target, name);
    }

    public static Thread newThread(String name, Runnable target) {
        return new Thread(target, name);
    }

}
