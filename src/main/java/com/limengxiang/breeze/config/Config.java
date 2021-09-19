package com.limengxiang.breeze.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * todo load config customizable
 *
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@ConfigurationProperties(prefix = "breeze")
@Data
@Slf4j
public class Config {

    public Config() {
        log.info("inited");
    }

    public enum DeployMode {
        single,
        cluster
        ;
    }

    private DeployMode deployMode;

    private int instanceId;

    private boolean startWorkers;

    private int jobQueueBusySize;

    private int jobThreadPoolBusySize;

    private int jobScanBatchSize;

    private int coordinatorDutyMaxPeriod;

    public boolean startWorkers() {
        return startWorkers;
    }

}
