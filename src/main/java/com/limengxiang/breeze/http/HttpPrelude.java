package com.limengxiang.breeze.http;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class HttpPrelude {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERR_DEFAULT = 1;
    public static final int CODE_INVALID_PARAM = 2;
    public static final String MsgOK = "ok";

    public static final int CODE_AUTH_ERR = 401;
    public static final int CODE_PR_ERR = 400;

    public static final String HEADER_APPID = "AppId";
    public static final String HEADER_APP_TOKEN = "AppToken";

    public static final String URI_JOB_CREATE = "/job/create";
    public static final String URI_JOB_UPDATE = "/job/update";
    public static final String URI_JOB_DELETE = "/job/delete";
    public static final String URI_JOB_DISABLE = "/job/disable";
    public static final String URI_JOB_RECALL = "/job/recall";
    public static final String URI_JOB_STAT = "/job/stat";
    public static final String URI_JOB_DETAIL = "/job/detail";
    public static final String URI_JOB_LOGS = "/job/logs";

    public static final String URI_EXEC_CREATE = "/executor/create";
    public static final String URI_EXEC_UPDATE = "/executor/update";
    public static final String URI_EXEC_DELETE = "/executor/delete";
    public static final String URI_EXEC_DISABLE = "/executor/disable";
    public static final String URI_EXEC_RECALL = "/executor/recall";
    public static final String URI_EXEC_DETAIL = "/executor/detail";

    public static final String URI_AUTH_CHECK_TOKEN = "/auth/checkToken";
}
