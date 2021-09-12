package com.limengxiang.breeze.manager;

import com.limengxiang.breeze.config.Config;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JdkCoordinator implements ICoordinator {

    private Config config;

    private volatile long cursorTick;

    private final Object lock = new Object();

    private final int dutyPeriod;

    public JdkCoordinator() {
        cursorTick = System.currentTimeMillis() / 1000;
        dutyPeriod = 10;
    }

    public JdkCoordinator(long cursorTick) {
        this.cursorTick = cursorTick;
        dutyPeriod = 10;
    }

    public JdkCoordinator(long cursorTick, int dutyPeriod) {
        this.cursorTick = cursorTick;
        this.dutyPeriod = dutyPeriod;
    }

    public JdkCoordinator(Config config) {
        this.config = config;
        cursorTick = System.currentTimeMillis() / 1000;
        dutyPeriod = config.getCoordinatorDutyPeriod();
    }

    public long getCursorTick() {
        return cursorTick;
    }

    @Override
    public DutyInfo acquireDuty() {
        synchronized (lock) {
            long ts = System.currentTimeMillis() / 1000;
            if (ts >= cursorTick) {
                long tickTo;
                // limit max duty period
                if (ts - cursorTick > dutyPeriod - 1) {
                    tickTo = cursorTick + dutyPeriod - 1;
                } else {
                    tickTo = ts;
                }
                DutyInfo dutyInfo = new DutyInfo();
                dutyInfo.setOn(true);
                dutyInfo.setTickFrom(cursorTick);
                dutyInfo.setTickTo(tickTo);
                cursorTick = tickTo + 1;
                return dutyInfo;
            }
            return null;
        }
    }
}
