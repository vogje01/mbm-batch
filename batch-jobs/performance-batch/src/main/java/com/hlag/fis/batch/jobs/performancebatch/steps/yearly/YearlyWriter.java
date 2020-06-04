package com.hlag.fis.batch.jobs.performancebatch.steps.yearly;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.writer.MysqlWriterBuilder;
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
public class YearlyWriter {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public YearlyWriter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @SuppressWarnings("unchecked")
    public ItemWriter<AgentPerformance> getWriter() {
        return new MysqlWriterBuilder<AgentPerformance>(entityManagerFactory).build();
    }
}
