package com.limengxiang.breeze.utils;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class Assertions {

    public static void notNull(Object arg) {
        if (arg == null) {
            throw new NullPointerException("Arg must not be null");
        }
    }

    public static void notNull(Object arg, String msg) {
        if (arg == null) {
            throw new NullPointerException(msg);
        }
    }
}
