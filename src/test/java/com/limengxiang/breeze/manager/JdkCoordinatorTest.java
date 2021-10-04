package com.limengxiang.breeze.manager;

import com.limengxiang.breeze.domain.coordinator.model.DutyInfo;
import com.limengxiang.breeze.domain.coordinator.model.JdkCoordinator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JdkCoordinatorTest {

    @SneakyThrows
    @Test
    public void test() {
        long startTick = System.currentTimeMillis() / 1000;
        JdkCoordinator coordinator = new JdkCoordinator();
        Assertions.assertEquals(coordinator.getCursorTick(), startTick);
        DutyInfo dutyInfo = coordinator.acquireDuty();
        Assertions.assertTrue(dutyInfo.isOn());
        Assertions.assertEquals(dutyInfo.getTickFrom(), startTick);
        Assertions.assertEquals(dutyInfo.getTickTo(), startTick);

        Thread.sleep(2000);

        DutyInfo dutyInfo1 = coordinator.acquireDuty();
        Assertions.assertTrue(dutyInfo1.isOn());
        Assertions.assertEquals(dutyInfo1.getTickFrom(), startTick + 1);
        Assertions.assertEquals(dutyInfo1.getTickTo(), startTick + 2);

        Thread.sleep(12000);

        DutyInfo dutyInfo2 = coordinator.acquireDuty();
        Assertions.assertTrue(dutyInfo2.isOn());
        Assertions.assertEquals(dutyInfo2.getTickFrom(), startTick + 3);
        Assertions.assertEquals(dutyInfo2.getTickTo(), startTick + 12);

        DutyInfo dutyInfo3 = coordinator.acquireDuty();
        Assertions.assertTrue(dutyInfo3.isOn());
        Assertions.assertEquals(dutyInfo3.getTickFrom(), startTick + 13);
        Assertions.assertEquals(dutyInfo3.getTickTo(), startTick + 14);

    }
}
