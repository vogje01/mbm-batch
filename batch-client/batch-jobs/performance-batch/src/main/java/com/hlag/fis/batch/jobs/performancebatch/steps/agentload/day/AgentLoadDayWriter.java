package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.day;

import com.momentum.batch.client.common.job.writer.writer.MysqlWriterBuilder;
import com.momentum.batch.database.domain.Agent;
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
