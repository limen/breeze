package com.limengxiang.breeze.http.aop;

import com.limengxiang.breeze.http.HttpPrelude;
import com.limengxiang.breeze.domain.auth.model.AuthCredential;
import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.exception.AuthException;
import com.limengxiang.breeze.exception.PermissionException;
import com.limengxiang.breeze.exception.WebServiceNotAvailableException;
import com.limengxiang.breeze.domain.auth.service.AuthService;
import com.limengxiang.breeze.http.OpEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Component
@Slf4j
public class AuthPointHandler implements PointHandler {

    private Config config;
    private AuthService authManager;

    @Autowired
    public AuthPointHandler(Config config, AuthService authManager) {
        this.config = config;
        this.authManager = authManager;
    }

    @Override
    public void before(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
        if (!config.isWebServer()) {
            throw new WebServiceNotAvailableException();
        }

        AuthCredential credential = authManager.getCredential(request.getHeader(HttpPrelude.HEADER_APPID));
        if (credential == null
                || credential.getToken() == null
                || !credential.getToken().equals(request.getHeader(HttpPrelude.HEADER_APP_TOKEN))
        ) {
            throw new AuthException();
        }
        if (!authManager.canDo(credential, OpEnum.fromRequest(request))) {
            throw new PermissionException();
        }
    }

    @Override
    public void after(ProceedingJoinPoint joinPoint, Object[] args, Object resp) {}
}
