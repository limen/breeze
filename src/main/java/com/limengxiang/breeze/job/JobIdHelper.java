package com.limengxiang.breeze.job;

import com.limengxiang.breeze.consts.UtilConst;

import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JobIdHelper {

    public static long firstOnTime(Date time) {
        long tick = getTick(time);
        return firstOnTime(tick);
    }

    public static long firstOnTime(long tick) {
        return (tick << UtilConst.JOB_ID_SEQ_WIDTH) + 1;
    }

    public static long lastOnTime(Date time) {
        long tick = getTick(time);
        return lastOnTime(tick);
    }

    public static long lastOnTime(long tick) {
        return tick << UtilConst.JOB_ID_SEQ_WIDTH | UtilConst.JOB_ID_SEQ_MAX;
    }

    public static long getSeqValue(Date time, long id) {
        return getSeqValue(getTick(time), id);
    }

    public static long getSeqValue(long time, long id) {
        return id - (time << 20);
    }

    public static long getTick(Date time) {
        return time.getTime() / 1000;
    }

}
