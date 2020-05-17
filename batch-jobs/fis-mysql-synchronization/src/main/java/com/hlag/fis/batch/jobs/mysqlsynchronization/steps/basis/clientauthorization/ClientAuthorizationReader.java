package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.clientauthorization;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class ClientAuthorizationReader {

    @Value("${clientAuthorization.chunkSize}")
    private int chunkSize;

    @Value("${clientAuthorization.cutOffDays}")
    private int cutOffDays;

    @Value("${users.fullSync}")
    private boolean fullSync;

    private EntityManagerFactory db2Emf;

    @Autowired
    public ClientAuthorizationReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    @SuppressWarnings("rawtypes")
    public ItemStreamReader getReader() {
        String queryString = fullSync ? "select c from ClientAuthorizationOld c" : "select c from ClientAuthorizationOld c where c.lastChange > " + DateTimeUtils.getCutOff(cutOffDays);
        return new CursorReaderBuilder<>(db2Emf)
                .queryString(queryString)
                .fetchSize(chunkSize)
                .build();
    }

}
