package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationlifecycle;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Reader for the documentation life cycle database synchronization.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.2
 */
@Component
public class DocumentationLifecycleReader {

    @Value("${documentationLifecycle.chunkSize}")
    private int chunkSize;

    @Value("${documentationLifecycle.cutOffHours}")
    private int cutOffHours;

    @Value("${documentationLifecycle.fullSync}")
    private boolean fullSync;

    private EntityManagerFactory db2Emf;

    @Autowired
    DocumentationLifecycleReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    /**
     * Reader for the documentation life cycles model processor.
     * <p>
     * The documentation requests are restricted by last change. Only documentation life cycles which have
     * a last change after now minus cutOffHours are processed.
     *
     * @return cursored reader for documentation life cycles.
     */
    ItemStreamReader getReader() {
        String queryString = fullSync ? "select l from DocumentationLifecycleOld l" : "select l from DocumentationLifecycleOld l where l.lastChange > " + DateTimeUtils.getCutOffHours(cutOffHours);
        return new CursorReaderBuilder(db2Emf)
                .queryString(queryString)
                .timeout(300)
                .queryHint("WITH CS")
                .fetchSize(chunkSize)
                .build();
    }
}
