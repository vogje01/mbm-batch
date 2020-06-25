package com.momentum.batch.server.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@SpringBootApplication(scanBasePackages = {"com.momentum.batch.common, com.momentum.batch.server"})
public class BatchListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchListenerApplication.class, args);
    }
}
