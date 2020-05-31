package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationrequest;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Reader for the documentation request processor.
 * <p>
 * Actually it is a courser reader. In order to be able t set the transaction isolation level, the hibernate DB2Dialect
 * needed be modified. If you need to set the transaction isolation level, use the ExtendedDB2Dialect for the hibernate
 * data source.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.2
 */
@Component
public class DocumentationRequestReader {

    @Value("${documentationRequest.chunkSize}")
    private int chunkSize;

    @Value("${documentationRequest.cutOffHours}")
    private int cutOffHours;

    private EntityManagerFactory db2Emf;

    @Autowired
    DocumentationRequestReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    /**
     * Reader for the documentation request model processor.
     * <p>
     * The documentation requests are restricted by last change. Only documentation requests which have
     * a last change after now minus cutOffHours are processed.
     *
     * @return cursored reader for documentation requests.
     */
    ItemStreamReader getReader() {
        return new CursorReaderBuilder(db2Emf)
                .queryString("select d from DocumentationRequestOld d where d.lastChange > " + DateTimeUtils.getCutOffHours(cutOffHours))
                .timeout(900)
                .queryHint("with CS")
                .fetchSize(chunkSize)
                .build();
    }
}
