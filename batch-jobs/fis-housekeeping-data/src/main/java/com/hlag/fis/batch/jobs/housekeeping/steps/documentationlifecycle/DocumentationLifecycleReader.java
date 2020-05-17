package com.hlag.fis.batch.jobs.housekeeping.steps.documentationlifecycle;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class DocumentationLifecycleReader {

    @Value("${shipment.documentation.documentationLifecycle.chunkSize}")
    private int chunkSize;

    @Value("${shipment.documentation.documentationLifecycle.houseKeepingDays}")
    private int houseKeepingDays;

    private EntityManagerFactory mysqlEmf;

    @Autowired
    DocumentationLifecycleReader(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        return new CursorReaderBuilder(mysqlEmf)
                .queryString("select l from DocumentationLifecycle l where l.lastChange < " + DateTimeUtils.getCutOff(houseKeepingDays))
                .fetchSize(chunkSize)
                .build();
    }
}
