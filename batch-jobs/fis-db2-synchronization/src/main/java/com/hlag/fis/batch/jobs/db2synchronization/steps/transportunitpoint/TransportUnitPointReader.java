package com.hlag.fis.batch.jobs.db2synchronization.steps.transportunitpoint;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class TransportUnitPointReader {

    @Value("${dbSync.shipment.booking.transportUnitPoint.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.shipment.booking.transportUnitPoint.cutOffHours}")
    private int cutOffHours;

    private EntityManagerFactory db2Emf;

    @Autowired
    public TransportUnitPointReader(@Qualifier("db2ProdEntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    /**
     * Paging reader.
     * <p>
     * As transport unit points are updated frequently, we need to make sure that the reader is not ending up in an
     * infinite loop. Therefore a paging reader is used with a ordering by last time descending.
     * </p>
     *
     * @return item reader.
     */
    ItemStreamReader getReader() {
        return new CursorReaderBuilder(db2Emf)
                .queryString("select t from TransportUnitPointOld t where t.lastChange > " + DateTimeUtils.getCutOffHours(cutOffHours))
                .timeout(300)
//                .queryHint("with CS")
                .fetchSize(chunkSize)
                .build();
    }
}
