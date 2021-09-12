package com.limengxiang.breeze.utils;

import java.io.*;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class FileUtil {

    public static final String BASE_PATH;

    static {
        BASE_PATH = System.getProperty("user.dir") + "/";
    }

    public static String read(String path) {
        File file = new File(path.startsWith("/") ? path : BASE_PATH + path);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            int step = 1000;
            while (true) {
                byte[] buff = new byte[1000];
                int readWidth = fis.read(buff);
                sb.append(StrUtil.toChars(buff));
                if (readWidth < step) {
                    break;
                }
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
