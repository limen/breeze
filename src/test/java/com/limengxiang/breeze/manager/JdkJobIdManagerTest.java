package com.limengxiang.breeze.manager;

import com.limengxiang.breeze.domain.job.model.JdkJobIdProvider;
import com.limengxiang.breeze.domain.job.JobIdHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JdkJobIdManagerTest {

    @Test
    public void testNext() {
        JdkJobIdProvider manager = new JdkJobIdProvider(null);
        Assertions.assertEquals(JobIdHelper.getSeqValue(1, manager.nextOnTime(1)), 1);
        Assertions.assertEquals(JobIdHelper.getSeqValue(1, manager.nextOnTime(1)), 2);
        Assertions.assertEquals(JobIdHelper.getSeqValue(2, manager.nextOnTime(2)), 1);
        Assertions.assertEquals(JobIdHelper.getSeqValue(3, manager.nextOnTime(3)), 1);
        Assertions.assertEquals(JobIdHelper.getSeqValue(4, manager.nextOnTime(4)), 1);
        Assertions.assertEquals(JobIdHelper.getSeqValue(5, manager.nextOnTime(5)), 1);
        Assertions.assertEquals(JobIdHelper.getSeqValue(6, manager.nextOnTime(6)), 1);
        Assertions.assertEquals(JobIdHelper.getSeqValue(7, manager.nextOnTime(7)), 1);
    }
}
