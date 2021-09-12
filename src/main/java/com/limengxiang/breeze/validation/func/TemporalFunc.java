package com.limengxiang.breeze.validation.func;

import com.limengxiang.breeze.utils.StrUtil;
import com.limengxiang.breeze.validation.Validation;
import com.limengxiang.breeze.validation.annotation.Temporal;

import java.lang.annotation.Annotation;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class TemporalFunc implements ValidateFunc {
    @Override
    public Boolean apply(Annotation an, Object v) {
        if (!((Temporal) an).required() && StrUtil.isEmpty((String) v)) {
            return true;
        }
        return Validation.temporal((String) v);
    }

    @Override
    public String applyForMsg(Annotation an, Object v) {
        if (!apply(an, v)) {
            return ((Temporal) an).msg();
        }
        return null;
    }
}
