package com.limengxiang.breeze.domain.auth;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class AuthPrelude {

    public static final int PR_SUPER = 4096;

    public static final int JOB_PR_CREATE = 1;
    public static final int JOB_PR_DELETE = 2;
    public static final int JOB_PR_UPDATE = 4;

    public static final int EXEC_PR_CREATE = 128;
    public static final int EXEC_PR_DELETE = 256;
    public static final int EXEC_PR_UPDATE = 512;

}
