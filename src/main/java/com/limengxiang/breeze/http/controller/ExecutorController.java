package com.limengxiang.breeze.http.controller;

import com.limengxiang.breeze.http.aop.AuditPoint;
import com.limengxiang.breeze.http.aop.AuthPoint;
import com.limengxiang.breeze.http.HttpPrelude;
import com.limengxiang.breeze.http.response.RespEntity;
import com.limengxiang.breeze.http.response.RespFactory;
import com.limengxiang.breeze.domain.executor.service.ExecutorService;
import com.limengxiang.breeze.domain.executor.model.ExecutorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@RestController
public class ExecutorController {

    private ExecutorService executorService;

    @Autowired
    public ExecutorController(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @AuditPoint
    @PostMapping(HttpPrelude.URI_EXEC_CREATE)
    @Transactional
    public RespEntity create(@RequestBody ExecutorEntity entity) {
        entity.validate().throwOnError();
        executorService.create(entity);
        return RespFactory.success(entity.getId());
    }

    @AuthPoint
    @GetMapping(HttpPrelude.URI_EXEC_DETAIL)
    public RespEntity detail(@RequestParam("id") Integer id) {
        return RespFactory.success(executorService.find(id));
    }

    @AuditPoint
    @PostMapping(HttpPrelude.URI_EXEC_DELETE)
    public RespEntity delete() {
        return null;
    }

    @AuditPoint
    @PostMapping(HttpPrelude.URI_EXEC_UPDATE)
    public RespEntity update() {
        return null;
    }

    @AuditPoint
    @PostMapping(HttpPrelude.URI_EXEC_DISABLE)
    public RespEntity disable() {
        return null;
    }

    @AuditPoint
    @PostMapping(HttpPrelude.URI_EXEC_RECALL)
    public RespEntity recall() {
        return null;
    }

}
