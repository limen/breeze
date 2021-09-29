package com.limengxiang.breeze.exception.handler;

import com.limengxiang.breeze.http.HttpPrelude;
import com.limengxiang.breeze.exception.AuthException;
import com.limengxiang.breeze.exception.InvalidParamException;
import com.limengxiang.breeze.exception.PermissionException;
import com.limengxiang.breeze.exception.WebServiceNotAvailableException;
import com.limengxiang.breeze.http.response.RespEntity;
import com.limengxiang.breeze.http.response.RespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public RespEntity handleAuthException(HttpServletRequest request, Exception ex) {
        return RespFactory.error(HttpPrelude.CODE_AUTH_ERR, "Not authorized");
    }

    @ExceptionHandler(PermissionException.class)
    public RespEntity handlePermissionException(HttpServletRequest request, Exception ex) {
        return RespFactory.error(HttpPrelude.CODE_PRIV_ERR, "Not permitted");
    }

    @ExceptionHandler(InvalidParamException.class)
    public RespEntity handleInvalidParam(HttpServletRequest request, InvalidParamException ex) {
        return RespFactory.raw(HttpPrelude.CODE_INVALID_PARAM, "Invalid param", ex.getErrors());
    }

    @ExceptionHandler(WebServiceNotAvailableException.class)
    public RespEntity handleWebServiceNotAvailable(HttpServletRequest request, Exception ex) {
        return RespFactory.error(HttpPrelude.CODE_ERR_DEFAULT, "Web service not available");
    }

    @ExceptionHandler(Exception.class)
    public RespEntity handleThrowable(HttpServletRequest request, Exception ex) {
        log.info(">>>> Request error, " + request.getMethod() + " " + request.getRequestURL());
        ex.printStackTrace();
        return RespFactory.error(HttpPrelude.CODE_ERR_DEFAULT, ex.getMessage(), ex.getStackTrace());
    }
}
