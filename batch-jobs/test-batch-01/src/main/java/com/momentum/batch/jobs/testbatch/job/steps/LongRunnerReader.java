package com.momentum.batch.jobs.testbatch.job.steps;

import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.logging.BatchStepLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

import static java.text.MessageFormat.format;
import static org.hibernate.internal.CoreLogging.logger;

@Component
public class LongRunnerReader {

    @BatchStepLogger(value = "Long Runner")
    private static Logger logger = LoggerFactory.getLogger(LongRunnerReader.class);

    ItemReader<User> reader() {

        final AtomicInteger count = new AtomicInteger(0);
        return () -> {

            try {
                Thread.sleep(300000);
                if (count.incrementAndGet() > 12) {
                    return null;
                }
                return new User();
            } catch (InterruptedException ex) {
                logger(format("Long runner interrupted - error: {0}", ex.getMessage()));
            }
            return null;
        };
    }
}
