package com.hlag.fis.batch.jobs.db2synchronization.steps.transportunitpoint;

import com.hlag.fis.batch.writer.Db2WriterBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
@EnableJpaRepositories(entityManagerFactoryRef = "db2DeveEntityManagerFactory")
public class TransportUnitPointWriter {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    TransportUnitPointWriter(@Qualifier("db2DeveEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public ItemWriter getWriter() {
        return new Db2WriterBuilder(entityManagerFactory).build();
    }
}
