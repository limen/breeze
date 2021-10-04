package com.limengxiang.breeze.domain.job;

import com.limengxiang.breeze.domain.job.model.JobQueueJdkImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JdkJobQueueTest {

    @Test
    public void testJobQueue() {
        JobQueueJdkImpl jobQueue = new JobQueueJdkImpl();
        jobQueue.push(Arrays.asList(1L,2L,3L));
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
