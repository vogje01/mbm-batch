package com.hlag.fis.batch.jobs.housekeepingbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hlag.fis.batch.configuration", "com.hlag.fis.batch"})
public class HousekeepingBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousekeepingBatchApplication.class, args);
    }
}
