package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.userrole;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class UserRoleReader {

    @Value("${userRole.chunkSize}")
    private int chunkSize;

    @Value("${userRole.cutOffDays}")
    private int cutOffDays;

    @Value("${users.fullSync}")
    private boolean fullSync;

    private EntityManagerFactory db2Emf;

    @Autowired
    UserRoleReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    @SuppressWarnings("rawtypes")
    ItemStreamReader getReader() {
        String queryString = fullSync ? "select u from UserRoleOld u " : "select u from UserRoleOld u where u.lastChange > " + DateTimeUtils.getCutOff(cutOffDays);
        return new CursorReaderBuilder(db2Emf)
                .queryString(queryString)
                .fetchSize(chunkSize)
                .build();
    }

}
