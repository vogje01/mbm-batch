package com.hlag.fis.batch.jobs.performancedataconsolidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hlag.fis.db", "com.hlag.fis.batch", "com.hlag.fis.batch.configuration"})
public class PerformanceConsolidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceConsolidationApplication.class, args);
    }
}
