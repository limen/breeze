package com.limengxiang.breeze.httpio.request;

import com.limengxiang.breeze.validation.annotation.Length;
import lombok.Data;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Data
public class CheckTokenEntity extends AbstractEntity {

    @Length(min = 1, max = 200, msg = "length should be 1-200")
    private String appId;

    @Length(min = 5, max = 200, msg = "length should be 5-200")
    private String token;

}
