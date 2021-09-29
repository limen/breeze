package com.limengxiang.breeze.http.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Configuration
@Aspect
@Slf4j
public class AopConfig {

    @Pointcut("@annotation(com.limengxiang.breeze.http.aop.AuthPoint)")
    public void pointcutAuth() {}

    @Pointcut("@annotation(com.limengxiang.breeze.http.aop.AuditPoint)")
    public void pointcutAudit() {}

    @Around("pointcutAuth()")
    public Object handleAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        return handlePoint(joinPoint, AuthPoint.class);
    }

    @Around("pointcutAudit()")
    public Object handleAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        return handlePoint(joinPoint, AuditPoint.class);
    }

    private <T extends Annotation> Object handlePoint(ProceedingJoinPoint joinPoint, Class<T> aspect) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Class<?> targetCls = joinPoint.getTarget().getClass();
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = targetCls.getMethod(ms.getName(), ms.getParameterTypes());
        T aspectPoint = method.getAnnotation(aspect);

        if (aspectPoint == null) {
            return joinPoint.proceed();
        }
        PointType[] pointTypes = new PointType[]{};
        if (aspectPoint instanceof AuditPoint) {
            pointTypes = ((AuditPoint) aspectPoint).types();
        } else if (aspectPoint instanceof AuthPoint) {
            pointTypes = ((AuthPoint) aspectPoint).types();
        }

        for (PointType pointType : pointTypes) {
            PointHandlerFactory.getHandler(pointType).before(joinPoint, request);
        }
        Object p = joinPoint.proceed();
        for (PointType pointType : pointTypes) {
            PointHandlerFactory.getHandler(pointType).after(joinPoint, joinPoint.getArgs(), p);
        }
        return p;
    }

}
