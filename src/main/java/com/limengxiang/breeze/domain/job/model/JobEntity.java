package com.limengxiang.breeze.domain.job.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.limengxiang.breeze.utils.JSONUtil;
import com.limengxiang.breeze.utils.StrUtil;
import lombok.Data;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class JobEntity {

    protected long jobId;
    protected String jobName;
    protected Date scheduleAt;
    protected int executorId;
    protected int status;
    protected String jobParams;
    protected Date createdAt;

    @JsonIgnore
    protected Map<String, Object> mappedParams;

    public Map<String, Object> mappedParams() {
        if (mappedParams != null) {
            return mappedParams;
        }
        if (StrUtil.isEmpty(jobParams)) {
            mappedParams = Collections.emptyMap();
        } else {
            mappedParams = JSONUtil.parse(jobParams);
        }
        return mappedParams;
    }

}
