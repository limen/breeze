package com.limengxiang.breeze.executor;

import com.limengxiang.breeze.consts.ExecutorConst;
import com.limengxiang.breeze.model.entity.JobEntity;
import com.limengxiang.breeze.utils.HttpUtil;
import com.limengxiang.breeze.utils.JSONUtil;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Map;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class HttpExecutor extends AbstractExecutor {

    @Data
    public static class HttpConfig {
        private String uri;
        private HttpMethod method;
        private String codeName;
        private Object codeOK;
        private Map<String, String> headers;
    }

    private HttpConfig httpConfig;

    private Map<String, Object> params;

    protected void parseConfig() {
        this.httpConfig = JSONUtil.parse(entity.getConfig(), HttpConfig.class);
        this.params = JSONUtil.parse(entity.getParams(), Map.class);
    }

    public ExecResult exec(JobEntity job) {
        ExecResult execResult = new ExecResult();
        HttpHeaders httpHeaders = new HttpHeaders();
        if (httpConfig.headers != null) {
            for (String key : httpConfig.headers.keySet()) {
                httpHeaders.set(key, httpConfig.headers.get(key));
            }
        }
        ResponseEntity<String> responseEntity = null;
        Exception ex = null;
        try {
            responseEntity = HttpUtil.call(httpConfig.uri, httpConfig.method, this.params, httpHeaders, String.class);
        } catch (Exception e) {
            ex = e;
        }
        if (ex == null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            Map body = JSONUtil.parse(responseEntity.getBody(), Map.class);
            Object codeValue = body.get(this.httpConfig.codeName);
            execResult.setExecStatus(codeValue != null && codeValue.equals(this.httpConfig.codeOK) ?
                    ExecutorConst.ExecStatus.sync_ok : ExecutorConst.ExecStatus.sync_err);
        } else {
            execResult.setExecStatus(ExecutorConst.ExecStatus.sync_err);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ex == null ? responseEntity.getBody() : ex.getMessage());
        sb.append(" << ");
        sb.append(JSONUtil.stringify(entity));
        execResult.setContext(sb.length() > 2048 ? sb.substring(0, 2048) : sb.toString());
        execResult.setEnd(new Date());
        return execResult;
    }
}
