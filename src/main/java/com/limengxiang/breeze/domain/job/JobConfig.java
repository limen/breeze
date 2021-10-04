package com.limengxiang.breeze.domain.job;

import com.limengxiang.breeze.config.Config;
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
            return new JobQueueRedisImpl(redisOps, config.getJobQueueBusySize());
        }
        log.info("Using JDK job queue");
        return new JobQueueJdkImpl(config.getJobQueueBusySize());
    }

    @Bean
    public IJobIdProvider jobIdManagerBean(@Autowired Config config,
                                           @Autowired JobService jobService,
                                           @Autowired(required = false) RedisOps redisOps) {
        if (config.getDeployMode().equals(Config.DeployMode.cluster)) {
            log.info("Using Redis job id provider");
            return new JobIdProviderRedisImpl(redisOps, jobService);
        }
        log.info("Using JDK job id provider");
        return new JobIdProviderJdkImpl(jobService);
    }

}
