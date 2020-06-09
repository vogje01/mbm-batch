package com.momentum.batch.client.jobs.common.writer;

import com.momentum.batch.common.domain.PrimaryKeyIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JpaItemWriter;

import javax.persistence.EntityManagerFactory;

/**
 * MySQL writer builder.
 */
public class MysqlWriterBuilder<T extends PrimaryKeyIdentifier> {

    private static final Logger logger = LoggerFactory.getLogger(MysqlWriterBuilder.class);

    private String topic;

    private EntityManagerFactory entityManagerFactory;

    public MysqlWriterBuilder(EntityManagerFactory entityManagerFactory) {
        entityManagerFactory(entityManagerFactory);
        logger.debug("MySQL writer initialized");
    }

    public MysqlWriterBuilder<T> entityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        logger.debug("Entity manager factory initialized");
        return this;
    }

    public void topic(String topic) {
        this.topic = topic;
        logger.debug("Topic initialized - name: " + topic);
    }

    public JpaItemWriter build() {
        MysqlWriter<T> writer = new MysqlWriter<>(entityManagerFactory);
        writer.setTopic(topic);
        return writer.getWriter();
    }
}
