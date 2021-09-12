package com.limengxiang.breeze.exception.handler;

import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.consts.ApiConst;
import com.limengxiang.breeze.exception.AuthException;
import com.limengxiang.breeze.exception.InvalidParamException;
import com.limengxiang.breeze.exception.PermissionException;
import com.limengxiang.breeze.httpio.response.RespEntity;
import com.limengxiang.breeze.httpio.response.RespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @Autowired
    private Config config;

    @ExceptionHandler(AuthException.class)
    public RespEntity handleAuthException(HttpServletRequest request, Exception ex) {
        return RespFactory.error(ApiConst.CODE_AUTH_ERR, "Not authorized");
    }

    @ExceptionHandler(PermissionException.class)
    public RespEntity handlePrivException(HttpServletRequest request, Exception ex) {
        return RespFactory.error(ApiConst.CODE_PRIV_ERR, "Not permitted");
    }

    @ExceptionHandler(InvalidParamException.class)
    public RespEntity handleInvalidParam(HttpServletRequest request, InvalidParamException ex) {
        return RespFactory.raw(ApiConst.CODE_INVALID_PARAM, "Invalid param", ex.getErrors());
    }

    @ExceptionHandler(Exception.class)
    public RespEntity handleThrowable(HttpServletRequest request, Exception ex) {
        log.info(">>>> Request error, " + request.getMethod() + " " + request.getRequestURL());
        ex.printStackTrace();
        return RespFactory.error(ApiConst.CODE_ERR_DEFAULT, ex.getMessage(), ex.getStackTrace());
    }
}
