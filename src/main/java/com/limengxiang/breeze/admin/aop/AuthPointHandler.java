package com.limengxiang.breeze.admin.aop;

import com.limengxiang.breeze.auth.AuthCredential;
import com.limengxiang.breeze.auth.AuthHelper;
import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.consts.UtilConst;
import com.limengxiang.breeze.exception.AuthException;
import com.limengxiang.breeze.exception.PermissionException;
import com.limengxiang.breeze.manager.AuthManager;
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

    @Autowired
    private Config config;

    @Autowired
    private AuthManager appTokenManager;

    @Override
    public void before(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
        AuthCredential credential = appTokenManager.getCredential(request.getHeader(UtilConst.HEADER_APPID));
        if (credential == null
                || credential.getToken() == null
                || !credential.getToken().equals(request.getHeader(UtilConst.HEADER_APP_TOKEN))
        ) {
            throw new AuthException();
        }
        if (!AuthHelper.canDo(credential, request)) {
            throw new PermissionException();
        }
    }

    @Override
    public void after(ProceedingJoinPoint joinPoint, Object[] args, Object resp) {}
}
