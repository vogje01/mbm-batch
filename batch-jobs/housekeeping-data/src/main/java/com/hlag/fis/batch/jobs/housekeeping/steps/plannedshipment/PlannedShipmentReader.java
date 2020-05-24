package com.hlag.fis.batch.jobs.housekeeping.steps.plannedshipment;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Planned shipment housekeeping reader.
 *
 * @author Jens.Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Component
public class PlannedShipmentReader {

    @Value("${shipment.documentation.documentationRequest.chunkSize}")
    private int chunkSize;

    @Value("${shipment.documentation.documentationRequest.houseKeepingDays}")
    private int houseKeepingDays;

    private EntityManagerFactory mysqlEmf;

    @Autowired
    PlannedShipmentReader(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        return new CursorReaderBuilder(mysqlEmf)
                .queryString("select p from PlannedShipment p where p.lastChange < " + DateTimeUtils.getCutOff(houseKeepingDays))
                .fetchSize(chunkSize)
                .build();
    }
}
