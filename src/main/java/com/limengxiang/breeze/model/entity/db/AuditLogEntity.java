package com.limengxiang.breeze.model.entity.db;

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
    private Object req;
    private Object resp;
    private Date createdAt;

}
