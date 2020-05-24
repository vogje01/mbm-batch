package com.hlag.fis.batch.jobs.db2synchronization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.hlag.fis.db.db2", "com.hlag.fis.batch", "com.hlag.fis.batch.jobs.db2synchronization.repository"})
public class DatabaseSynchronizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseSynchronizationApplication.class, args);
    }
}
