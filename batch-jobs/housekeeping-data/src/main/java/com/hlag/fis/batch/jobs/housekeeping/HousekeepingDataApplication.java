package com.hlag.fis.batch.jobs.housekeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.hlag.fis.db", "com.hlag.fis.batch"})
public class HousekeepingDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousekeepingDataApplication.class, args);
    }

}
