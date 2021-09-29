package com.limengxiang.breeze.http.request;

import com.limengxiang.breeze.validation.annotation.Temporal;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class JobStatQueryEntity extends RequestEntity {

    public JobStatQueryEntity(HttpServletRequest request) {
        fromTime = request.getParameter("fromTime");
        toTime = request.getParameter("toTime");
    }

    @Temporal
    private String fromTime;

    @Temporal
    private String toTime;

    @Override
    protected void internalValidate() {
        super.internalValidate();
        if (getErrors() == null) {
            if (toTime.compareTo(fromTime) <= 0) {
                addError("toTime", "must after fromTime");
            }
        }
    }
}
