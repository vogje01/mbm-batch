package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationinstruction;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Reader for the documentation instruction processor.
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @since 0.0.2
 */
@Component
public class DocumentationInstructionReader {

    @Value("${documentationInstruction.chunkSize}")
    private int chunkSize;

    @Value("${documentationInstruction.cutOffHours}")
    private int cutOffHours;

    @Value("${documentationInstruction.fullSync}")
    private boolean fullSync;

    private EntityManagerFactory db2Emf;

    @Autowired
    public DocumentationInstructionReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    /**
     * Reader for the documentation instruction processor.
     * <p>
     * The documentation requests are restricted by last change. Only documentation requests which have
     * a last change after now minus cutOffHours are processed.
     *
     * @return cursored reader for documentation instruction.
     */
    ItemStreamReader getReader() {
        String queryString = fullSync ? "select d from DocumentationInstructionOld" : "select d from DocumentationInstructionOld d where d.lastChange > " + DateTimeUtils.getCutOffHours(cutOffHours);
        return new CursorReaderBuilder(db2Emf)
                .queryString(queryString)
                .fetchSize(chunkSize)
                .build();
    }
}
