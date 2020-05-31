package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.transportunitpoint;

import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.plannedshipment.PlannedShipmentReader;
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

/**
 * Transport unit point reader.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
public class TransportUnitPointReader {

    private static final Logger logger = LoggerFactory.getLogger(PlannedShipmentReader.class);

    @Value("${transportUnitPoint.chunkSize}")
    private int chunkSize;

    @Value("${transportUnitPoint.cutOffHours}")
    private int cutOffHours;

    @Value("${transportUnitPoint.fullSync}")
    private boolean fullSync;

    private EntityManagerFactory db2Emf;

    @Autowired
    public TransportUnitPointReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    /**
     * Cursor reader.
     * <p>
     * As transport unit points are updated frequently, we need to make sure that the reader is not ending up in an
     * infinite loop. Therefore a paging reader is used with a ordering by last time descending.
     * </p>
     *
     * @return item reader.
     */
    ItemStreamReader getReader() {
        logger.info("Starting transport unit point reader - chunkSize: " + chunkSize + " cutOffHours: " + cutOffHours);
        String queryString = fullSync ? "select t from TransportUnitPointOld t" : "select t from TransportUnitPointOld t where t.lastChange > " + DateTimeUtils.getCutOffHours(cutOffHours);
        return new CursorReaderBuilder(db2Emf)
                .queryString(queryString)
                .timeout(300)
                .fetchSize(chunkSize)
                .build();
    }
}
