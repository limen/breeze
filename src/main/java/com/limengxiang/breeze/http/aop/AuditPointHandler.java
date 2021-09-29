package com.limengxiang.breeze.http.aop;

import com.limengxiang.breeze.http.HttpPrelude;
import com.limengxiang.breeze.model.AuditLogService;
import com.limengxiang.breeze.model.entity.db.AuditLogEntity;
import com.limengxiang.breeze.utils.JSONUtil;
import com.limengxiang.breeze.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
@Slf4j
public class AuditPointHandler implements PointHandler {

    private AuditLogService auditLogService;

    @Autowired
    public AuditPointHandler(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Override
    public void before(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
    }

    @Override
    public void after(ProceedingJoinPoint joinPoint, Object[] args, Object resp) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        AuditLogEntity logEntity = new AuditLogEntity();
        logEntity.setAppId(request.getHeader(HttpPrelude.HEADER_APPID));
        logEntity.setUri(request.getRequestURI());
        logEntity.setIp(request.getRemoteAddr());
        logEntity.setReq(StrUtil.trimToLength(JSONUtil.stringify(args), 1024));
        logEntity.setResp(StrUtil.trimToLength(JSONUtil.stringify(resp), 1024));

        auditLogService.create(logEntity);
    }
}
