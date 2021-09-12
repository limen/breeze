package com.limengxiang.breeze.admin.aop;

import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public interface PointHandler {


    void before(ProceedingJoinPoint joinPoint, HttpServletRequest request);

    void after(ProceedingJoinPoint joinPoint, HttpServletRequest request);

}
