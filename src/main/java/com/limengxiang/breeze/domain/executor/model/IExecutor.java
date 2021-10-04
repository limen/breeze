package com.limengxiang.breeze.domain.executor.model;

import java.util.Map;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public interface IExecutor {

    ExecResult exec(Map<String, Object> params);

}
