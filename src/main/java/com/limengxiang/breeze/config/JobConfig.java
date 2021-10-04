package com.limengxiang.breeze.config;

import com.limengxiang.breeze.domain.job.model.*;
import com.limengxiang.breeze.domain.job.service.JobService;
import com.limengxiang.breeze.redis.RedisOps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Configuration
@Slf4j
public class JobConfig {

    public JobConfig() {
        log.info("inited");
    }

    @Bean
    public IJobQueue jobQueueBean(@Autowired Config config,
                                  @Autowired RedisOps redisOps) {
        if (config.getDeployMode().equals(Config.DeployMode.cluster)) {
            log.info("Using Redis job queue");
            return new RedisJobQueue(redisOps);
        }
        log.info("Using JDK job queue");
        return new JdkJobQueue();
    }

    @Bean
    public IJobIdProvider jobIdManagerBean(@Autowired Config config,
                                           @Autowired JobService jobService,
                                           @Autowired(required = false) RedisOps redisOps) {
        if (config.getDeployMode().equals(Config.DeployMode.cluster)) {
            log.info("Using Redis job id provider");
            RedisJobIdProvider jobIdManager = new RedisJobIdProvider(redisOps, jobService);
            jobIdManager.setConfig(config);
            return jobIdManager;
        }
        log.info("Using JDK job id provider");
        JdkJobIdProvider jobIdManager = new JdkJobIdProvider(jobService);
        jobIdManager.setConfig(config);
        return jobIdManager;
    }

}
