package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.messagespecification;

import com.hlag.fis.batch.writer.MysqlWriterBuilder;
import com.hlag.fis.db.mysql.model.MessageSpecification;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class MessageSpecificationWriter {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    MessageSpecificationWriter(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @SuppressWarnings("rawtypes")
    public ItemWriter getWriter() {
        return new MysqlWriterBuilder<MessageSpecification>(entityManagerFactory).build();
    }
}
