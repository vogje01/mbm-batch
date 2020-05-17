package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.userauthorization;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class UserAuthorizationReader {

    @Value("${userAuthorization.chunkSize}")
    private int chunkSize;

    @Value("${userAuthorization.cutOffDays}")
    private int cutOffDays;

    @Value("${userAuthorization.fullSync}")
    private boolean fullSync;

    private EntityManagerFactory db2Emf;

    @Autowired
    UserAuthorizationReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    @SuppressWarnings("rawtypes")
    public ItemStreamReader getReader() {
        String queryString = fullSync ? "select u from UserAuthorizationOld u" : "select u from UserAuthorizationOld u where u.lastChange > " + DateTimeUtils.getCutOff(cutOffDays);
        return new CursorReaderBuilder(db2Emf)
                .queryString(queryString)
                .fetchSize(chunkSize)
                .build();
    }
}
