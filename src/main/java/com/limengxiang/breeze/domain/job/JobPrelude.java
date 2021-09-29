package com.limengxiang.breeze.domain.job;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JobPrelude {

    public static final int JOB_ID_SEQ_WIDTH = 20;
    public static final long JOB_ID_SEQ_MAX = (1 << JOB_ID_SEQ_WIDTH) - 1;
    public static final String JOB_ID_SEQ_PREFIX = "brz_job_id:seq:";
    public static final String JOB_QUEUE_KEY = "brz_job_queue";
}
