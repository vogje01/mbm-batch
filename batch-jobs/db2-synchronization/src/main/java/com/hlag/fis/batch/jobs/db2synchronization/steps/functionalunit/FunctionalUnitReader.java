package com.hlag.fis.batch.jobs.db2synchronization.steps.functionalunit;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class FunctionalUnitReader {

    @Value("${dbSync.basis.functionalUnit.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.functionalUnit.cutOffDays}")
    private int cutOffDays;

    private EntityManagerFactory db2Emf;

    @Autowired
    FunctionalUnitReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    ItemStreamReader getReader() {
        return new CursorReaderBuilder(db2Emf).entityManagerFactory(db2Emf)
                .queryString("select f from FunctionalUnitOld f where f.lastChange > " + DateTimeUtils.getCutOff(cutOffDays))
                .fetchSize(chunkSize).build();
    }
}
