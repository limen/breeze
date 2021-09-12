package com.limengxiang.breeze.validation.annotation;

import com.limengxiang.breeze.validation.TypeEnum;

import java.lang.annotation.*;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Type {
    TypeEnum type();
}
