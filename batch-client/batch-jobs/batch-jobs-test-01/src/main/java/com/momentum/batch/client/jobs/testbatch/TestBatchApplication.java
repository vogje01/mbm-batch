package com.momentum.batch.client.jobs.testbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.momentum.batch.client.jobs"})
public class TestBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestBatchApplication.class, args);
    }
}
