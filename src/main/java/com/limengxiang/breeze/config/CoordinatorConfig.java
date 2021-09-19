package com.limengxiang.breeze.config;

import com.limengxiang.breeze.manager.ICoordinator;
import com.limengxiang.breeze.manager.JdkCoordinator;
import com.limengxiang.breeze.manager.RedisCoordinator;
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
            return new RedisCoordinator(config);
        }
        return new JdkCoordinator(config);
    }

}
