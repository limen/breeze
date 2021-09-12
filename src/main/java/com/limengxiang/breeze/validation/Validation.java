package com.limengxiang.breeze.validation;

import com.limengxiang.breeze.validation.func.LengthFunc;
import com.limengxiang.breeze.validation.func.RangeFunc;
import com.limengxiang.breeze.validation.func.TemporalFunc;
import com.limengxiang.breeze.validation.func.ValidateFunc;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class Validation {

    public static Pattern temporalPattern = Pattern.compile("^[1-9]\\d{3}-(0[1-9]|1[0-2])-([0-2]\\d|3[0-1]) ([0-1]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$");

    private static final ValidateFunc temporalFunc;
    private static final ValidateFunc rangeFunc;
    private static final ValidateFunc lengthFunc;

    static {
        temporalFunc = new TemporalFunc();
        rangeFunc = new RangeFunc();
        lengthFunc = new LengthFunc();
    }

    public static boolean temporal(String v) {
        return temporalPattern.matcher(v).find();
    }

    public static boolean range(Number v, Number min, Number max) {
        if (v == null) {
            return false;
        }
        return v.doubleValue() >= min.doubleValue() && v.doubleValue() <= max.doubleValue();
    }

    public static boolean length(String v, int min, int max) {
        if (v == null) {
            return false;
        }
        return v.length() >= min && v.length() <= max;
    }

    public static boolean apply(TypeEnum id, Annotation ann, Object value) {
        return getFunc(id).apply(ann, value);
    }

    public static String applyForMsg(TypeEnum id, Annotation ann, Object value) {
        return getFunc(id).applyForMsg(ann, value);
    }

    private static ValidateFunc getFunc(TypeEnum id) {
        switch (id) {
            case Temporal:
                return temporalFunc;
            case Range:
                return rangeFunc;
            case Length:
                return lengthFunc;
            default:
                throw new RuntimeException("Unsupported identifier: " + id);
        }
    }

}
