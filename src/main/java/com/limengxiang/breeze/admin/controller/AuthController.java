package com.limengxiang.breeze.admin.controller;

import com.limengxiang.breeze.auth.AuthCredential;
import com.limengxiang.breeze.consts.ApiConst;
import com.limengxiang.breeze.httpio.request.CheckTokenEntity;
import com.limengxiang.breeze.httpio.response.RespEntity;
import com.limengxiang.breeze.httpio.response.RespFactory;
import com.limengxiang.breeze.manager.AuthManager;
import com.limengxiang.breeze.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@RestController
public class AuthController {

    @Autowired
    private AuthManager authManager;

    @PostMapping(ApiConst.URI_AUTH_CHECK_TOKEN)
    public RespEntity checkToken(@RequestBody CheckTokenEntity entity) {
        entity.validate().throwOnError();

        if (StrUtil.isEmpty(entity.getToken()) || StrUtil.isEmpty(entity.getAppId())) {
            return RespFactory.error("Invalid param");
        }

        AuthCredential credential = authManager.getCredential(entity.getAppId());

        if (credential != null && credential.getToken().equals(entity.getToken())) {
            return RespFactory.success();
        }

        return RespFactory.error();
    }

}
