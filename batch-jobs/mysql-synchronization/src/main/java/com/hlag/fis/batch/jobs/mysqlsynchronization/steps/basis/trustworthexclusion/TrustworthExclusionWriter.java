package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.trustworthexclusion;

import com.hlag.fis.batch.writer.MysqlWriterBuilder;
import com.hlag.fis.db.mysql.model.TrustworthExclusion;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class TrustworthExclusionWriter {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    TrustworthExclusionWriter(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @SuppressWarnings("rawtypes")
    public ItemWriter getWriter() {
        return new MysqlWriterBuilder<TrustworthExclusion>(entityManagerFactory).build();
    }
}
