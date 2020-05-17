package com.hlag.fis.batch.jobs.db2synchronization.steps.userrole;

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

    @Value("${dbSync.basis.userRole.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.userRole.cutOffDays}")
    private int cutOffDays;

    private EntityManagerFactory db2Emf;

    @Autowired
    UserRoleReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    public ItemStreamReader getReader() {
        return new CursorReaderBuilder(db2Emf)
            .queryString("select u from UserRoleOld u where u.lastChange > " + DateTimeUtils.getCutOff(cutOffDays))
            .fetchSize(chunkSize)
            .build();
    }

}
