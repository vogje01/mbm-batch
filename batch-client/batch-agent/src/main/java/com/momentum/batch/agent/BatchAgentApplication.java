package com.momentum.batch.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = {"com.hlag.fis.batch.util", "com.hlag.fis.batch.configuration", "com.hlag.fis.batch.agent"})
public class BatchAgentApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        SpringApplication.run(BatchAgentApplication.class, args);
    }
}
