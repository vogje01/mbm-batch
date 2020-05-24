package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.functionalunit;

import com.hlag.fis.batch.writer.MysqlWriterBuilder;
import com.hlag.fis.db.mysql.model.FunctionalUnit;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class FunctionalUnitWriter {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    FunctionalUnitWriter(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @SuppressWarnings("rawtypes")
    public ItemWriter getWriter() {
        return new MysqlWriterBuilder<FunctionalUnit>(entityManagerFactory).build();
    }
}