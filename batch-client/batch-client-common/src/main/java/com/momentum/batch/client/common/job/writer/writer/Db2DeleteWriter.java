package com.momentum.batch.client.common.job.writer.writer;

import com.momentum.batch.domain.PrimaryKeyIdentifier;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManagerFactory;

/**
 * Deletes a list of entities from a DB2 database.
 *
 * @param <T> entity class type.
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
public class Db2DeleteWriter<T extends PrimaryKeyIdentifier> extends AbstractDeleteWriter<T> {

    @Autowired
    @Qualifier("db2ProdEntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public JpaItemWriter getWriter() {
        return getWriter(entityManagerFactory);
    }
}
