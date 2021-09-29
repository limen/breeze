package com.limengxiang.breeze.http.request;

import com.limengxiang.breeze.utils.StrUtil;
import com.limengxiang.breeze.validation.annotation.Length;
import com.limengxiang.breeze.validation.annotation.Range;
import com.limengxiang.breeze.validation.annotation.Temporal;
import lombok.Data;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class JobPostEntity extends RequestEntity {
    @Temporal(required = false)
    private String execAt;

    @Range(min = 0, max = 3600, required = false)
    private Integer execAfter;

    @Length(min = 1, max = 64)
    private String jobName;

    @Range(min = 1, max = Integer.MAX_VALUE)
    private Long executorId;

    @Length(min = 0, max = 1024, required = false)
    private String params;

    @Override
    protected void internalValidate() {
        super.internalValidate();
        if (StrUtil.isEmpty(execAt) && execAfter == null) {
            addError("execAt|execAfter","execAt and execAfter are alternative");
        }
    }
}
