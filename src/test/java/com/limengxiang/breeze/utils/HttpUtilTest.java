package com.limengxiang.breeze.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class HttpUtilTest {

    @Test
    public void testHttps() {
        try {
            ResponseEntity<String> resp = HttpUtil.get("https://www.qycloud.com.cn/api2/user/info", null, null, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
