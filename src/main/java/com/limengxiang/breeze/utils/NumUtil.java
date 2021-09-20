package com.limengxiang.breeze.utils;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class NumUtil {

    /**
     * 字符串转Long
     *
     * "123.5" => 123L
     * ".5" => 0L
     * "0.5" => 0L
     *
     * @param s
     * @return
     */
    public static Long toLong(String s) {
        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException("数据格式不正确：" + s);
        }
        int dotPos = s.indexOf(".");
        if (dotPos < 0) {
            return Long.parseLong(s);
        } else {
            return Double.valueOf(s).longValue();
        }
    }

    public static Integer toInteger(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return Integer.parseInt(StrUtil.valueOf(o));
    }

    public static Long toLong(Object s) {
        if (s == null) {
            return null;
        }
        String s1 = StrUtil.valueOf(s);
        return toLong(s1);
    }
}
