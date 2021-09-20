package com.limengxiang.breeze.validation.func;

import com.limengxiang.breeze.validation.Validation;
import com.limengxiang.breeze.validation.annotation.Range;

import java.lang.annotation.Annotation;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class RangeFunc implements ValidateFunc {
    @Override
    public Boolean apply(Annotation annotation, Object v) {
        Range range = (Range) annotation;
        if (v == null) {
            return !range.required();
        }
        return Validation.range((Number) v, range.min(), range.max());
    }

    @Override
    public String applyForMsg(Annotation an, Object v) {
        if (!apply(an, v)) {
            return ((Range) an).msg();
        }
        return null;
    }
}
