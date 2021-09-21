package com.limengxiang.breeze.manager;

import com.limengxiang.breeze.SpringContextUtil;
import com.limengxiang.breeze.config.Config;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Slf4j
public class Commander {

    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.warn("Shutdown job manager");
            JobManager.shutdown();
        }));
    }

    public static void startAllWorkers() {
        registerShutdownHook();

        Config conf = SpringContextUtil.getBean(Config.class);

        if (conf.isJobConsumer()) {
            log.info("Start job manager");
            JobManager.start(conf);
        } else {
            log.info("Start without job manager");
        }
    }

}
