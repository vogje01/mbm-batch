package com.momentum.batch.client.jobs.housekeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.momentum.batch.client.jobs"})
public class HousekeepingBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousekeepingBatchApplication.class, args);
    }
}
