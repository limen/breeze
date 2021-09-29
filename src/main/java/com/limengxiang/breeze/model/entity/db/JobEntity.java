package com.limengxiang.breeze.model.entity.db;

import lombok.Data;

import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class JobEntity {

    protected long jobId;
    protected String jobName;
    protected Date scheduleAt;
    protected int executorId;
    protected int status;
    protected String jobParams;
    protected Date createdAt;

}
