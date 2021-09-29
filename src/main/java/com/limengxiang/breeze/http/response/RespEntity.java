package com.limengxiang.breeze.http.response;

import com.limengxiang.breeze.http.HttpPrelude;
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
        this(code, HttpPrelude.MsgOK, null);
    }

    public RespEntity(int code, Object data) {
        this(code, HttpPrelude.MsgOK, data);
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
