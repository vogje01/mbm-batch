package com.hlag.fis.batch.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hlag.fis.batch.util", "com.hlag.fis.batch.manager"})
public class BatchManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchManagerApplication.class, args);
    }
}
