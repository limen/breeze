package com.limengxiang.breeze.http.controller;

import com.limengxiang.breeze.http.response.RespEntity;
import com.limengxiang.breeze.http.response.RespFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@RestController
public class TestController {

    @PostMapping("/test/echo")
    public RespEntity echo(@RequestBody(required = false) Object body) {
        return RespFactory.success(body);
    }

}
