package com.limengxiang.breeze.httpio.response;

import com.limengxiang.breeze.consts.ApiConst;
import lombok.Data;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class RespEntity {
    private int code;
    private String msg;
    private Object data;
    private StackTraceElement[] trace;

    public RespEntity(int code) {
        this(code, ApiConst.MsgOK, null);
    }

    public RespEntity(int code, Object data) {
        this(code, ApiConst.MsgOK, data);
    }

    public RespEntity(int code, String msg) {
        this(code, msg, null);
    }

    public RespEntity(int code, String msg, StackTraceElement[] trace) {
        this.code = code;
        this.msg = msg;
        this.trace = trace;
    }

    public RespEntity(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
