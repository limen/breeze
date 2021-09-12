package com.limengxiang.breeze.consts;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class UtilConst {

    public static final String COO_LOCK_KEY = "brz_coordinator_lock";
    public static final String COO_TICK_KEY = "brz_coordinator_tick";
    public static final String JOB_QUEUE_KEY = "brz_job_queue";
    public static final String JOB_ID_SEQ_PREFIX = "brz_job_id:seq:";

    public static final int JOB_ID_SEQ_WIDTH = 20;
    public static final long JOB_ID_SEQ_MAX = (1 << JOB_ID_SEQ_WIDTH) - 1;

    public static final String HEADER_APPID = "AppId";
    public static final String HEADER_APP_TOKEN = "AppToken";

    public static final String DATE_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
    public static final String DATE_FORMAT_MIN = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_HOUR = "yyyy-MM-dd HH";
    public static final String DATE_EXPR_FORMAT_SEC = "yyyyMMdd/HH:mm:ss";
    public static final String DATE_EXPR_FORMAT_MIN = "yyyyMMdd/HH:mm";
    public static final String DATE_EXPR_FORMAT_HOUR = "yyyyMMdd/HH";
    public static final String DATE_EXPR_FORMAT_DAY = "yyyyMMdd";
    public static final String DATE_EXPR_FORMAT_MONTH = "yyyyMM";
    public static final String DATE_EXPR_FORMAT_YEAR = "yyyy";
}
