package com.momentum.batch.client.jobs.performance.steps.daily;

import com.momentum.batch.client.jobs.common.writer.MysqlWriterBuilder;
import com.momentum.batch.server.database.domain.BatchPerformance;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Daily performance consolidation writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@Component
public class DailyWriter {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public DailyWriter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @SuppressWarnings("unchecked")
    public ItemWriter<BatchPerformance> getWriter() {
        return new MysqlWriterBuilder<BatchPerformance>(entityManagerFactory).build();
    }
}
