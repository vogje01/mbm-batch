package com.momentum.batch.client.jobs.performance.steps.agentload.week;

import com.momentum.batch.client.jobs.common.writer.MysqlWriterBuilder;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.BatchPerformance;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Job execution info delete writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@Component
public class AgentLoadWeekWriter {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public AgentLoadWeekWriter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @SuppressWarnings("unchecked")
    public ItemWriter<Agent> getWriter() {
        return new MysqlWriterBuilder<BatchPerformance>(entityManagerFactory).build();
    }
}
