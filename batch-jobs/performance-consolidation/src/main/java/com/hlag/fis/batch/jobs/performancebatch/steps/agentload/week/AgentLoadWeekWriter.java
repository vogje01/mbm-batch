package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.week;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.writer.MysqlWriterBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class AgentLoadWeekWriter {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public AgentLoadWeekWriter(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @SuppressWarnings("unchecked")
    public ItemWriter<Agent> getWriter() {
        return new MysqlWriterBuilder<BatchPerformance>(entityManagerFactory).build();
    }
}
