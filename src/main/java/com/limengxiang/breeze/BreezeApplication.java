package com.limengxiang.breeze;

import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.domain.job.JobManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.limengxiang.breeze")
@EnableConfigurationProperties(Config.class)
public class BreezeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreezeApplication.class, args);
        JobManager.start();
    }

}
