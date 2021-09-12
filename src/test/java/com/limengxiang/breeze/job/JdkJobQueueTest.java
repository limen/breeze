package com.limengxiang.breeze.job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JdkJobQueueTest {

    @Test
    public void testJobQueue() {
        JdkJobQueue jobQueue = new JdkJobQueue();
        jobQueue.push(Arrays.asList(1,2,3));
        Assertions.assertEquals(jobQueue.size(), 3);
        Assertions.assertEquals(jobQueue.pop(), 1);
        Assertions.assertEquals(jobQueue.size(), 2);
        Assertions.assertEquals(jobQueue.pop(), 2);
        Assertions.assertEquals(jobQueue.size(), 1);
        Assertions.assertEquals(jobQueue.pop(), 3);
        Assertions.assertEquals(jobQueue.size(), 0);
        Assertions.assertNull(jobQueue.pop());
    }
}
