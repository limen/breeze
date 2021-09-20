package com.limengxiang.breeze.admin.controller;

import com.limengxiang.breeze.admin.aop.AuthPoint;
import com.limengxiang.breeze.httpio.request.AuditLogQueryEntity;
import com.limengxiang.breeze.httpio.response.RespEntity;
import com.limengxiang.breeze.httpio.response.RespFactory;
import com.limengxiang.breeze.model.AuditLogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@RestController
public class AuditLogController {

    @Autowired
    private AuditLogModel auditLogModel;

    @AuthPoint
    @GetMapping("/auditLog/list")
    public RespEntity logs(HttpServletRequest request) {
        AuditLogQueryEntity entity = new AuditLogQueryEntity(request);
        entity.validate().throwOnError();
        return RespFactory.success(auditLogModel.find(entity));
    }

}
