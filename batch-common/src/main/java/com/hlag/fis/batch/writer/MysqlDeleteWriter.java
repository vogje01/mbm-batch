package com.hlag.fis.batch.writer;

import com.hlag.fis.batch.domain.PrimaryKeyIdentifier;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;

/**
 * Deletes a list of entities from a MySQL database.
 *
 * @param <T> entity class type.
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
public class MysqlDeleteWriter<T extends PrimaryKeyIdentifier<String>> extends AbstractDeleteWriter<T> {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public JpaItemWriter getWriter() {
        return getWriter(entityManagerFactory);
    }
}
