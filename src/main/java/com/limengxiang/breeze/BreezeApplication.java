package com.limengxiang.breeze;

import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.manager.Commander;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.limengxiang.breeze")
@EnableConfigurationProperties(Config.class)
@MapperScan("com.limengxiang.breeze.model.dao")
public class BreezeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreezeApplication.class, args);
        Commander.startAllWorkers();
    }

}
