package com.momentum.batch.client.jobs.performance.steps.monthly;

import com.momentum.batch.client.jobs.common.writer.MysqlWriterBuilder;
import com.momentum.batch.database.domain.BatchPerformance;
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
public class MonthlyWriter {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public MonthlyWriter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @SuppressWarnings("unchecked")
    public ItemWriter<BatchPerformance> getWriter() {
        return new MysqlWriterBuilder<BatchPerformance>(entityManagerFactory).build();
    }
}
