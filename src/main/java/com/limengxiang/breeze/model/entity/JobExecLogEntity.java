package com.limengxiang.breeze.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class JobExecLogEntity {

    protected long jobId;
    protected int status;
    protected String context;
    protected int elapse;
    protected Date execAt;

}
