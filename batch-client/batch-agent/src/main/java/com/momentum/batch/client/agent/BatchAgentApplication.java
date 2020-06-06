package com.momentum.batch.client.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class BatchAgentApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        SpringApplication.run(BatchAgentApplication.class, args);
    }
}
