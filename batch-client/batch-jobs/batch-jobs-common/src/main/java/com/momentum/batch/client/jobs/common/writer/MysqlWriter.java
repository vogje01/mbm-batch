package com.momentum.batch.client.jobs.common.writer;

import com.momentum.batch.domain.PrimaryKeyIdentifier;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Abstract MySQL writer.
 *
 * @param <T> entity type
 */
public class MysqlWriter<T extends PrimaryKeyIdentifier> extends AbstractTopicWriter<T> {

    private EntityManagerFactory entityManagerFactory;

    private String topic;

    public MysqlWriter(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public JpaItemWriter getWriter() {
        JpaItemWriter itemWriter = new JpaItemWriter<T>() {
            @Override
            public void write(List<? extends T> items) {
                EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
                if (entityManager == null) {
                    throw new DataAccessResourceFailureException("Unable to obtain a transactional EntityManager");
                } else {
                    try {
                        entityManager.getTransaction().begin();
                        doWrite(entityManager, items);
                        entityManager.getTransaction().commit();
                        if (topic != null) {
                            items.forEach(d -> sendTopic(topic, d));
                        }
                    } catch (Throwable ex) {
                        JpaItemWriter.logger.error(format("SQL exception - message: {0}", ex.getMessage()), ex);
                    }
                }
            }
        };
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }
}
