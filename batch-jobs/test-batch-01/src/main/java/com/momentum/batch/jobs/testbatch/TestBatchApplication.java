package com.momentum.batch.jobs.testbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.momentum.batch", "com.hlag.fis.batch"})
public class TestBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestBatchApplication.class, args);
    }
}
