package com.hlag.fis.batch.jobs.db2synchronization.steps.geohierarchy;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class GeoHierarchyReader {

    @Value("${dbSync.organization.geoHierarchy.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.organization.geoHierarchy.cutOffDays}")
    private int cutOffDays;

    private EntityManagerFactory db2Emf;

    @Autowired
    public GeoHierarchyReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    ItemStreamReader getReader() {
        return new CursorReaderBuilder(db2Emf)
                .queryString("select g from GeoHierarchyOld g where g.lastChange > " + DateTimeUtils.getCutOff(cutOffDays))
                .timeout(300)
                .queryHint("WITH CS")
                .fetchSize(chunkSize)
                .build();
    }
}
