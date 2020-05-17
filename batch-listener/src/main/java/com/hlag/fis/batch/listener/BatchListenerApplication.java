package com.hlag.fis.batch.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hlag.fis.batch.configuration", "com.hlag.fis.batch.util", "com.hlag.fis.batch.agent", "com.hlag.fis.batch.listener"})
public class BatchListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchListenerApplication.class, args);
    }
}
