package com.limengxiang.breeze.validation.annotation;

import com.limengxiang.breeze.validation.TypeEnum;

import java.lang.annotation.*;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Type(type = TypeEnum.Temporal)
public @interface Temporal {
    String msg() default "not a temporal value";
    boolean required() default true;
}
