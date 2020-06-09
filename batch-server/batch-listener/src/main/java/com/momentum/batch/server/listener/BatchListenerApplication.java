package com.momentum.batch.server.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.momentum.batch.common.configuration, com.momentum.batch.server"})
public class BatchListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchListenerApplication.class, args);
    }
}
