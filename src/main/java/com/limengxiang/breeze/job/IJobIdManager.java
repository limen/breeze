package com.limengxiang.breeze.job;

import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public interface IJobIdManager {

    long nextOnTime(Date time);

    long nextOnTime(long time);

    long firstOnTime(Date time);

    long firstOnTime(long time);

    long lastOnTime(Date time);

    long lastOnTime(long time);

}
