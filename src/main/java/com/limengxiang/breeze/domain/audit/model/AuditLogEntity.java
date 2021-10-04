package com.limengxiang.breeze.domain.audit.model;

import com.limengxiang.breeze.utils.StrUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class AuditLogEntity {

    private Integer id;
    private String appId;
    private String ip;
    private String uri;
    private String req;
    private String resp;
    private Date createdAt;

    public void setReq(String v) {
        req = StrUtil.trimToLength(v, 1024);
    }

    public void setResp(String v) {
        resp = StrUtil.trimToLength(v, 1024);
    }

}
