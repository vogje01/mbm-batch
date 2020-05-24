package com.hlag.fis.batch.jobs.housekeeping.steps.docinstruction;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class DocumentationInstructionReader {

    @Value("${shipment.documentation.documentationInstruction.chunkSize}")
    private int chunkSize;

    @Value("${shipment.documentation.documentationInstruction.houseKeepingDays}")
    private int houseKeepingDays;

    private EntityManagerFactory mysqlEmf;

    @Autowired
    DocumentationInstructionReader(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        return new CursorReaderBuilder(mysqlEmf)
                .queryString("select i from DocumentationInstruction i"
                        + " left join fetch i.documentationLifecycles"
                        + " where i.lastChange < " + DateTimeUtils.getCutOff(houseKeepingDays))
                .fetchSize(chunkSize)
                .build();
    }
}
