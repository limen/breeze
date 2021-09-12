package com.limengxiang.breeze.validation.func;

import com.limengxiang.breeze.utils.StrUtil;
import com.limengxiang.breeze.validation.Validation;
import com.limengxiang.breeze.validation.annotation.Length;

import java.lang.annotation.Annotation;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class LengthFunc implements ValidateFunc {
    @Override
    public Boolean apply(Annotation an, Object v) {
        Length length = (Length) an;
        if (!length.required() && StrUtil.isEmpty((String) v)) {
            return true;
        }
        return Validation.length((String) v, length.min(), length.max());
    }

    @Override
    public String applyForMsg(Annotation an, Object v) {
        if (!apply(an, v)) {
            return ((Length) an).msg();
        }
        return null;
    }
}
