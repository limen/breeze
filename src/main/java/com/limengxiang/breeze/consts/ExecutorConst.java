package com.limengxiang.breeze.consts;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class ExecutorConst {

    public static final int STATUS_SYNC_OK = 100;
    public static final int STATUS_ASYNC_ACK_OK = 202;

    public static boolean isOKStatus(int status) {
        return status == STATUS_SYNC_OK || status == STATUS_ASYNC_ACK_OK;
    }

    public enum ExecStatus {
        sync_ok(100),
        sync_err(101),
        async_auto_ack(200),
        async_wait_ack(201),
        async_ack_ok(202),
        async_ack_err(203);

        private Integer code;

        ExecStatus(int code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    public enum ExecutorType {
        http,
        https,
        stdout,
        ;
    }

}
