package com.momentum.batch.jobs.testbatch.job.steps;

import com.hlag.fis.batch.domain.Agent;
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
public class AgentLoadDayWriter {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public AgentLoadDayWriter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @SuppressWarnings("unchecked")
    public ItemWriter<Agent> getWriter() {
        return new MysqlWriterBuilder<Agent>(entityManagerFactory).build();
    }
}
