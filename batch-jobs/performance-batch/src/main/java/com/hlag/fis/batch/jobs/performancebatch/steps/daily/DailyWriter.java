package com.hlag.fis.batch.jobs.performancebatch.steps.daily;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.writer.MysqlWriterBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Daily performance consolidation writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
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
