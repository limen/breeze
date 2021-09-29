package com.limengxiang.breeze.config;

import com.limengxiang.breeze.domain.coordinator.ICoordinator;
import com.limengxiang.breeze.domain.coordinator.JdkCoordinator;
import com.limengxiang.breeze.domain.coordinator.RedisCoordinator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Configuration
@Slf4j
public class CoordinatorConfig {

    public CoordinatorConfig() {
        log.info("inited");
    }

    @Bean
    public ICoordinator coordinatorBean(@Autowired Config config) {
        if (config.getDeployMode().equals(Config.DeployMode.cluster)) {
            log.info("Using Redis coordinator");
            return new RedisCoordinator(config);
        }
        log.info("Using JDK coordinator");
        return new JdkCoordinator(config);
    }

}
