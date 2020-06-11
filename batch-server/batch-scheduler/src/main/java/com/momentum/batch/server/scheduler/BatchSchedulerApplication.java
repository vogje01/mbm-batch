package com.momentum.batch.server.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.momentum.batch.common, com.momentum.batch.server"})
public class BatchSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchSchedulerApplication.class, args);
    }
}
