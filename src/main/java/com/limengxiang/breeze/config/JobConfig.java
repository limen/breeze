package com.limengxiang.breeze.config;

import com.limengxiang.breeze.job.*;
import com.limengxiang.breeze.model.JobModel;
import com.limengxiang.breeze.model.dao.RedisOps;
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
        if (config.getDeployMode().equals(Config.DeployMode.multi)) {
            return new RedisJobQueue(redisOps);
        }
        return new JdkJobQueue();
    }

    @Bean
    public IJobIdManager jobIdManagerBean(@Autowired Config config,
                                          @Autowired JobModel jobModel,
                                          @Autowired(required = false) RedisOps redisOps) {
        if (config.getDeployMode().equals(Config.DeployMode.multi)) {
            RedisJobIdManager jobIdManager = new RedisJobIdManager(redisOps, jobModel);
            jobIdManager.setConfig(config);
            return jobIdManager;
        }
        JdkJobIdManager jobIdManager = new JdkJobIdManager(jobModel);
        jobIdManager.setConfig(config);
        return jobIdManager;
    }

}
