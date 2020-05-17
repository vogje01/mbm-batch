package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.client;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class ClientReader {

    @Value("${client.chunkSize}")
    private int chunkSize;

    @Value("${client.cutOffDays}")
    private int cutOffDays;

    @Value("${client.fullSync}")
    private boolean fullSync;

    private EntityManagerFactory db2Emf;

    @Autowired
    public ClientReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    @SuppressWarnings("rawtypes")
    public ItemStreamReader getReader() {
        String queryString = fullSync ? "select c from ClientOld c" : "select c from ClientOld c where c.lastChange > " + DateTimeUtils.getCutOff(cutOffDays);
        return new CursorReaderBuilder(db2Emf)
                .queryString(queryString)
                .fetchSize(chunkSize)
                .build();
    }

}
