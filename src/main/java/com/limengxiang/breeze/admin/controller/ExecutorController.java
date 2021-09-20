package com.limengxiang.breeze.admin.controller;

import com.limengxiang.breeze.admin.aop.AuditPoint;
import com.limengxiang.breeze.admin.aop.AuthPoint;
import com.limengxiang.breeze.consts.ApiConst;
import com.limengxiang.breeze.httpio.response.RespEntity;
import com.limengxiang.breeze.httpio.response.RespFactory;
import com.limengxiang.breeze.model.ExecutorModel;
import com.limengxiang.breeze.model.entity.ExecutorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@RestController
public class ExecutorController {

    @Autowired
    private ExecutorModel executorModel;

    @AuditPoint
    @PostMapping(ApiConst.URI_EXEC_CREATE)
    @Transactional
    public RespEntity create(@RequestBody ExecutorEntity entity) {
        entity.validate().throwOnError();
        executorModel.create(entity);
        return RespFactory.success(entity.getId());
    }

    @AuthPoint
    @GetMapping(ApiConst.URI_EXEC_DETAIL)
    public RespEntity detail(@RequestParam("id") Integer id) {
        return RespFactory.success(executorModel.findOne(id));
    }

    @AuditPoint
    @PostMapping(ApiConst.URI_EXEC_DELETE)
    public RespEntity delete() {
        return null;
    }

    @AuditPoint
    @PostMapping(ApiConst.URI_EXEC_UPDATE)
    public RespEntity update() {
        return null;
    }

    @AuditPoint
    @PostMapping(ApiConst.URI_EXEC_DISABLE)
    public RespEntity disable() {
        return null;
    }

    @AuditPoint
    @PostMapping(ApiConst.URI_EXEC_RECALL)
    public RespEntity recall() {
        return null;
    }

}
