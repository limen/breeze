package com.limengxiang.breeze.exception;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class AuthException extends RuntimeException {

    public AuthException() {}

    public AuthException(String msg) {
        super(msg);
    }
}
