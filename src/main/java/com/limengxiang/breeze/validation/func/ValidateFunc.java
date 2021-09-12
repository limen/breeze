package com.limengxiang.breeze.validation.func;

import java.lang.annotation.Annotation;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public interface ValidateFunc {

    Boolean apply(Annotation an, Object v);

    String applyForMsg(Annotation ann, Object v);

}
