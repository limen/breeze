package com.limengxiang.breeze.validation.annotation;

import com.limengxiang.breeze.validation.TypeEnum;

import java.lang.annotation.*;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Type(type = TypeEnum.Range)
public @interface Range {
    String msg() default "value out of range";
    double min();
    double max();
    boolean required() default true;
}
