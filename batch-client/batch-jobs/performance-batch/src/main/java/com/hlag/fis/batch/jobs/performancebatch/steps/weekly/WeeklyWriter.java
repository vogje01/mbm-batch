package com.hlag.fis.batch.jobs.performancebatch.steps.weekly;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.momentum.batch.client.common.job.writer.writer.MysqlWriterBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Job execution info delete writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Component
public class WeeklyWriter {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public WeeklyWriter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @SuppressWarnings("unchecked")
    public ItemWriter<BatchPerformance> getWriter() {
        return new MysqlWriterBuilder<BatchPerformance>(entityManagerFactory).build();
    }
}
