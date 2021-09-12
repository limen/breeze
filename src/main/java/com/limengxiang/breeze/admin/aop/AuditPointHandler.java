package com.limengxiang.breeze.admin.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
@Slf4j
public class AuditPointHandler implements PointHandler {

    @Override
    public void before(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
    }

    @Override
    public void after(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
        // todo
    }
}
