package com.limengxiang.breeze.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class StrUtil {
    /**
     * 转换成字符串
     * @param obj
     * @return
     */
    public static String valueOf(Object obj) {
        if (obj instanceof Double || obj instanceof Float) {
            // 不以科学计数法表示浮点数
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            return nf.format(obj);
        }
        return obj == null ? "" : String.valueOf(obj);
    }

    public static String trimToLength(String input, int length) {
        if (input == null || length < 0 || input.length() <= length) {
            return input;
        }
        return input.substring(0, length);
    }

    /**
     * 是否空字符串或null
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str));
    }

    public static boolean notEmpty(String var) {
        return !isEmpty(var);
    }

    public static char[] toChars(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes).flip();
        CharBuffer cb = StandardCharsets.UTF_8.decode(bb);
        return cb.array();
    }
}
