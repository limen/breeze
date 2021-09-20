package com.limengxiang.breeze.admin.aop;

import com.limengxiang.breeze.consts.UtilConst;
import com.limengxiang.breeze.model.AuditLogModel;
import com.limengxiang.breeze.model.entity.AuditLogEntity;
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

    @Autowired
    private AuditLogModel auditLogModel;

    @Override
    public void before(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
    }

    @Override
    public void after(ProceedingJoinPoint joinPoint, Object[] args, Object resp) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        AuditLogEntity logEntity = new AuditLogEntity();
        logEntity.setAppId(request.getHeader(UtilConst.HEADER_APPID));
        logEntity.setUri(request.getRequestURI());
        logEntity.setIp(request.getRemoteAddr());
        logEntity.setReq(StrUtil.trimToLength(JSONUtil.stringify(args), 1024));
        logEntity.setResp(StrUtil.trimToLength(JSONUtil.stringify(resp), 1024));

        auditLogModel.insert(logEntity);
    }
}
