package com.hlag.fis.batch.jobs.db2synchronization.steps.plannedshipment;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class PlannedShipmentReader {

    @Value("${dbSync.shipment.booking.plannedShipment.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.shipment.booking.plannedShipment.cutOffHours}")
    private int cutOffHours;

    private EntityManagerFactory db2Emf;

    @Autowired
    public PlannedShipmentReader(@Qualifier("db2ProdEntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    public ItemStreamReader getReader() {
        return new CursorReaderBuilder(db2Emf)
                .queryString("select p from PlannedShipmentOld p where p.lastChange > " + DateTimeUtils.getCutOffHours(cutOffHours))
                .timeout(300)
                //.queryHint("WITH CS")
                .fetchSize(chunkSize)
                .build();
    }
}
