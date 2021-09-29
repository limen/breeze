package com.limengxiang.breeze.http.aop;

import java.lang.annotation.*;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthPoint {
    PointType[] types() default {PointType.auth, PointType.log};
}
