package com.limengxiang.breeze.http.controller;

import com.limengxiang.breeze.domain.auth.model.AuthCredential;
import com.limengxiang.breeze.domain.auth.service.AuthService;
import com.limengxiang.breeze.http.HttpPrelude;
import com.limengxiang.breeze.http.request.CheckTokenEntity;
import com.limengxiang.breeze.http.response.RespEntity;
import com.limengxiang.breeze.http.response.RespFactory;
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

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(HttpPrelude.URI_AUTH_CHECK_TOKEN)
    public RespEntity checkToken(@RequestBody CheckTokenEntity entity) {
        entity.validate().throwOnError();

        if (StrUtil.isEmpty(entity.getToken()) || StrUtil.isEmpty(entity.getAppId())) {
            return RespFactory.error("Invalid param");
        }

        AuthCredential credential = authService.getCredential(entity.getAppId());

        if (credential != null && credential.getToken().equals(entity.getToken())) {
            return RespFactory.success();
        }

        return RespFactory.error();
    }

}
