package com.limengxiang.breeze.domain.job;

import java.util.List;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public interface IJobQueue {

    long push(List<Long> jobIds);

    Long pop();

    long size();

}
