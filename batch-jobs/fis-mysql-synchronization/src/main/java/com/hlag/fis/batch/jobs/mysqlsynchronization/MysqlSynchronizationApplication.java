package com.hlag.fis.batch.jobs.mysqlsynchronization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages={"com.hlag.fis.db", "com.hlag.fis.batch"})
public class MysqlSynchronizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MysqlSynchronizationApplication.class, args);
    }
}
