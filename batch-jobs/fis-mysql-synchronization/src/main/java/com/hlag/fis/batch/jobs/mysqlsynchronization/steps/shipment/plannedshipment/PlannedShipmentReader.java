package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.plannedshipment;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class PlannedShipmentReader {

    private static final Logger logger = LoggerFactory.getLogger(PlannedShipmentReader.class);

    @Value("${plannedShipment.chunkSize}")
    private int chunkSize;

    @Value("${plannedShipment.cutOffHours}")
    private int cutOffHours;

    @Value("${plannedShipment.fullSync}")
    private boolean fullSync;

    private EntityManagerFactory db2Emf;

    @Autowired
    public PlannedShipmentReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    public ItemStreamReader getReader() {
        logger.info("Starting planned shipment reader - chunkSize: " + chunkSize + " cutOffHours: " + cutOffHours);
        String queryString = fullSync ? "select p from PlannedShipmentOld p" : "select p from PlannedShipmentOld p where p.lastChange > " + DateTimeUtils.getCutOffHours(cutOffHours);
        return new CursorReaderBuilder(db2Emf)
                .queryString(queryString)
                .fetchSize(chunkSize)
                .build();
    }
}
