package com.limengxiang.breeze.domain.coordinator;

import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.domain.coordinator.model.ICoordinator;
import com.limengxiang.breeze.domain.coordinator.model.CoordinatorJdkImpl;
import com.limengxiang.breeze.domain.coordinator.model.CoordinatorRedisImpl;
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
            return new CoordinatorRedisImpl(config);
        }
        log.info("Using JDK coordinator");
        return new CoordinatorJdkImpl(config);
    }

}
