package com.momentum.batch.server.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.momentum.batch.common, com.momentum.batch.server"})
public class BatchManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchManagerApplication.class, args);
    }
}
