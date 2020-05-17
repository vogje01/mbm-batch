package com.hlag.fis.batch.jobs.db2synchronization.steps.users;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class UsersReader {

    @Value("${dbSync.basis.users.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.users.cutOffDays}")
    private int cutOffDays;

    private EntityManagerFactory db2Emf;

    @Autowired
    public UsersReader(@Qualifier("db2ProdEntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    public ItemStreamReader getReader() {
        return new CursorReaderBuilder(db2Emf)
                .queryString("select u from UsersOld u where u.lastChange > " + DateTimeUtils.getCutOff(cutOffDays))
                .fetchSize(chunkSize)
                .build();
    }

}
