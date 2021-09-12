package com.limengxiang.breeze.utils;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class Assertions {

    public static void notNull(Object... args) {
        for (Object o : args) {
            if (o == null) {
                throw new RuntimeException("Args must not be null");
            }
        }
    }
}
