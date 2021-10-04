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

    private DeployMode deployMode = DeployMode.single;

    private int instanceId = 1;

    private int jobConsumerPoolSize = 2 * Runtime.getRuntime().availableProcessors();

    // To tolerant the latency, the job scanner should wait for a while after the clock passed.
    private int jobScannerWaitSeconds = 5;


    // start job consumer
    private boolean runJobConsumer = true;

    private boolean provideWebService = true;

    // if the job queue size approaches this value, the later jobs would be waiting.
    private int jobQueueBusySize = 1000;

    // if the job consumer thread pool size approaches this value, the dispatching thread would be waiting
    private int jobConsumerThreadPoolBusySize = 100;

    // to improve performance, 100 maybe appropriate
    private int jobScanBatchSize = 100;

    // If the current duty is later than the clock, the duty should be longer and limited by this value.
    private int coordinatorDutyMaxPeriod = 10;

    // to improve performance, we cached the executor object in seconds of this value
    private int executorCacheTime = 120;
    // how many executors we should cache, the upper boundary.
    private int executorCacheCapacity = 1000;

    public boolean runJobConsumer() {
        return runJobConsumer;
    }

    public boolean provideWebService() {
        return provideWebService;
    }

}
