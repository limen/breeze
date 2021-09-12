package com.limengxiang.breeze.utils;

import org.junit.jupiter.api.Test;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class FileUtilTest {

    @Test
    public void testRead() {
        String read = FileUtil.read("fixture/data/trigger-set.json");
    }
}
