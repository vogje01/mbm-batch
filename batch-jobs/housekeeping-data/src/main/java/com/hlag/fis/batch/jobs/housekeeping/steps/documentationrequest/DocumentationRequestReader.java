package com.hlag.fis.batch.jobs.housekeeping.steps.documentationrequest;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class DocumentationRequestReader {

    @Value("${shipment.documentation.documentationRequest.chunkSize}")
    private int chunkSize;

    @Value("${shipment.documentation.documentationRequest.houseKeepingDays}")
    private int houseKeepingDays;

    private EntityManagerFactory mysqlEmf;

    @Autowired
    DocumentationRequestReader(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        return new CursorReaderBuilder(mysqlEmf)
                .queryString("select d from DocumentationRequest d"
                        + " left join fetch d.documentationInstructions"
                        + " left join fetch d.documentationLifecycles"
                        + " where d.lastChange < " + DateTimeUtils.getCutOff(houseKeepingDays)
                )
                .fetchSize(chunkSize)
                .build();
    }
}
