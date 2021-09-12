package com.limengxiang.breeze.model.entity;

import com.limengxiang.breeze.httpio.request.AbstractEntity;
import com.limengxiang.breeze.validation.annotation.Length;
import lombok.Data;

import java.util.Date;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class ExecutorEntity extends AbstractEntity {

    private Integer id;

    @Length(min = 1, max = 64)
    private String name;

    private String type;

    @Length(min = 1, max = 1024)
    private String config;

    @Length(min = 0, max = 1024)
    private String params;

    private Date createdAt;
    private Date updatedAt;

}
