package com.limengxiang.breeze.validation.annotation;

import com.limengxiang.breeze.validation.TypeEnum;

import java.lang.annotation.*;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Type(type = TypeEnum.Length)
public @interface Length {
    String msg() default "length out of range";
    int min();
    int max();
    boolean required() default true;
}
