package com.limengxiang.breeze.http.controller;

import com.limengxiang.breeze.http.aop.AuthPoint;
import com.limengxiang.breeze.http.request.AuditLogQueryEntity;
import com.limengxiang.breeze.http.response.RespEntity;
import com.limengxiang.breeze.http.response.RespFactory;
import com.limengxiang.breeze.model.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@RestController
public class AuditLogController {

    private AuditLogService auditLogService;

    @Autowired
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @AuthPoint
    @GetMapping("/auditLog/list")
    public RespEntity logs(HttpServletRequest request) {
        AuditLogQueryEntity entity = new AuditLogQueryEntity(request);
        entity.validate().throwOnError();
        return RespFactory.success(auditLogService.find(entity));
    }

}
