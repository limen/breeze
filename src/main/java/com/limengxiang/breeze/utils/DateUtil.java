package com.limengxiang.breeze.utils;

import com.limengxiang.breeze.validation.Validation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class DateUtil {

    public static Date parse(String date) throws RuntimeException {
        if (StrUtil.isEmpty(date)) {
            return null;
        }
        try {
            return parse(date, Validation.DATE_FORMAT_SEC);
        } catch (Exception ex) {
            throw new RuntimeException("Parse data error:" + ex.getMessage() + ", input:" + date);
        }
    }

    public static Date parse(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage() + ",date=" + date);
        }
    }

    public static String format(Date d) {
        return format(d, Validation.DATE_FORMAT_SEC);
    }

    public static String format(Date d, String fmt) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(d);
    }

    /**
     * 日期转时间戳(s)
     * @param d
     * @return
     */
    public static Long toTimestamp(Date d) {
        return d.getTime() / 1000;
    }

    /**
     * 毫秒时间戳
     * @param date
     * @return
     */
    public static Long toMilliTimestamp(Date date) {
        return date.getTime();
    }
}
