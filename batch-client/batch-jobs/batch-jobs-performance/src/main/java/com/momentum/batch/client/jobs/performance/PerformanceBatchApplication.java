package com.momentum.batch.client.jobs.performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.momentum.batch.client.jobs"})
public class PerformanceBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceBatchApplication.class, args);
    }
}
