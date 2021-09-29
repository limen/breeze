package com.limengxiang.breeze.http.response;

import com.limengxiang.breeze.http.HttpPrelude;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class RespFactory {

    public static RespEntity raw(Integer code, String msg, Object data) {
        return new RespEntity(code, msg, data);
    }

    public static RespEntity success() {
        return raw(HttpPrelude.CODE_SUCCESS, "", null);
    }

    public static RespEntity success(Object data) {
        return raw(HttpPrelude.CODE_SUCCESS, "", data);
    }

    public static RespEntity error(Integer code, String msg) {
        return raw(code, msg, null);
    }

    public static RespEntity error() {
        return raw(HttpPrelude.CODE_ERR_DEFAULT, "", null);
    }

    public static RespEntity error(String msg) {
        return raw(HttpPrelude.CODE_ERR_DEFAULT, msg, null);
    }

    public static RespEntity error(Integer code, String msg, StackTraceElement[] trace) {
        return new RespEntity(code, msg, trace);
    }

}
