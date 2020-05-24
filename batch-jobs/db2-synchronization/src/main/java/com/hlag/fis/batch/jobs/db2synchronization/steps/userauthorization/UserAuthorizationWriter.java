package com.hlag.fis.batch.jobs.db2synchronization.steps.userauthorization;

import com.hlag.fis.batch.writer.Db2WriterBuilder;
import com.hlag.fis.db.db2.model.UserAuthorizationOld;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class UserAuthorizationWriter {

    @Autowired
    @Qualifier("db2DeveEntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    public ItemWriter getWriter() {
        return new Db2WriterBuilder<UserAuthorizationOld>(entityManagerFactory).build();
    }
}
