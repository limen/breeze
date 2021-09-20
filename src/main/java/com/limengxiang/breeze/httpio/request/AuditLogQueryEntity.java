package com.limengxiang.breeze.httpio.request;

import com.limengxiang.breeze.utils.NumUtil;
import com.limengxiang.breeze.utils.StrUtil;
import com.limengxiang.breeze.validation.annotation.Length;
import com.limengxiang.breeze.validation.annotation.Temporal;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class AuditLogQueryEntity extends AbstractEntity {

    @Temporal
    private String fromTime;
    @Temporal
    private String toTime;
    @Length(min = 0, max = 256, required = false)
    private String appId;
    private String uri;
    private Integer fromId;
    private Integer limit;

    public AuditLogQueryEntity(HttpServletRequest request) {
        fromTime = request.getParameter("fromTime");
        toTime = request.getParameter("toTime");
        appId = request.getParameter("appId");
        uri = request.getParameter("uri");
        if (StrUtil.notEmpty(request.getParameter("fromId"))) {
            fromId = NumUtil.toInteger(request.getParameter("fromId"));
        }
        if (StrUtil.notEmpty(request.getParameter("limit"))) {
            limit = NumUtil.toInteger(request.getParameter("limit"));
        }
    }

    @Override
    protected void internalValidate() {
        super.internalValidate();
        if (getErrors() != null) {
            return;
        }
        if (toTime.compareTo(fromTime) <= 0) {
            addError("time", "toTime must be later than fromTime");
        }
    }
}
