package com.momentum.batch.jobs.testbatch.job.steps;

import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.repository.AgentRepository;
import com.momentum.batch.client.common.job.builder.BatchStepBuilder;
import org.slf4j.Logger;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

import static java.text.MessageFormat.format;

@Configuration
public class ShortRunnerStep {

    private static final String STEP_NAME = "Short running step";

    private static final int chunkSize = 5;

    private Logger batchLogger;

    @Bean
    public Step shortRunner(BatchStepBuilder<User, User> stepBuilder,
                            final Logger batchLogger1,
                            AgentRepository agentRepository,
                            ItemReader<User> reader,
                            ItemProcessor<User, User> processor,
                            ItemWriter<User> writer) {
        this.batchLogger = batchLogger1;

        long totalCount = agentRepository.count();
        batchLogger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .total(totalCount)
                .build();
    }

    @Bean
    public ItemReader<User> reader() {
        final AtomicInteger count = new AtomicInteger(0);
        return () -> {
            if (count.incrementAndGet() > 12) {
                return null;
            }
            batchLogger.debug("Step reading");
            return new User();
        };
    }

    @Bean
    public ItemProcessor<User, User> processor() {
        return user -> {
            try {
                Thread.sleep(1000);
                return new User();
            } catch (InterruptedException ex) {
                batchLogger.error(format("Long runner interrupted - error: {0}", ex.getMessage()));
            }
            return null;
        };
    }

    @Bean
    public ItemWriter<User> getWriter() {
        return list -> {
            batchLogger.debug(format("List written"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                batchLogger.error(format("Long runner interrupted - error: {0}", ex.getMessage()));
            }
        };
    }
}
