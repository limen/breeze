package com.limengxiang.breeze.exception;

import lombok.Data;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class InvalidParamException extends RuntimeException {

    private Object errors;

    public InvalidParamException(Object errors) {
        this.errors = errors;
    }

}
