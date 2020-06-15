package com.momentum.batch.client.jobs.common.writer;

import com.momentum.batch.server.database.domain.PrimaryKeyIdentifier;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Iterator;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Delete item writer.
 * <p>
 * Deletes all items in the provided list using a single transaction. The transaction is opened new for the given chunk.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
public class AbstractDeleteWriter<T extends PrimaryKeyIdentifier> extends AbstractTopicWriter<T> {

    public JpaItemWriter getWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter writer = new JpaItemWriter<T>() {
            @Override
            public void write(List<? extends T> items) {
                EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
                if (entityManager == null) {
                    throw new DataAccessResourceFailureException("Unable to obtain a transactional EntityManager");
                } else {
                    if (items.isEmpty()) {
                        JpaItemWriter.logger.debug("Empty item list provided");
                        return;
                    }
                    entityManager.getTransaction().begin();
                    Iterator var5 = items.iterator();
                    while (var5.hasNext()) {
                        T item = (T) var5.next();
                        if (!entityManager.contains(item)) {
                            item = (T) entityManager.find(item.getClass(), item.getId());
                        }
                        if (item != null) {
                            entityManager.remove(item);
                        }
                    }
                    entityManager.flush();
                    entityManager.getTransaction().commit();
                    JpaItemWriter.logger.debug(format("Entities deleted - count: {0}", items.size()));
                }
            }
        };
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
