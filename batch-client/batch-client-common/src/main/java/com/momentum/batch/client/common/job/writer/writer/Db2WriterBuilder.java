package com.momentum.batch.client.common.job.writer.writer;

import com.momentum.batch.domain.PrimaryKeyIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import javax.persistence.EntityManagerFactory;

/**
 * Abstract DB2 writer builder.
 */
public class Db2WriterBuilder<T extends PrimaryKeyIdentifier> {

    private static final Logger logger = LoggerFactory.getLogger(Db2WriterBuilder.class);

    private String topic;

    private EntityManagerFactory entityManagerFactory;

    public Db2WriterBuilder(EntityManagerFactory entityManagerFactory) {
        entityManagerFactory(entityManagerFactory);
    }

    public Db2WriterBuilder entityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        return this;
    }

    public void topic(String topic) {
        this.topic = topic;
    }

    public ItemWriter build() {
        Db2Writer<T> writer = new Db2Writer<>(entityManagerFactory);
        writer.setTopic(topic);
        return writer.getWriter();
    }
}
