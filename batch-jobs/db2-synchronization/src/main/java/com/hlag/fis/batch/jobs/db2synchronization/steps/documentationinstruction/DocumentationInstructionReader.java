package com.hlag.fis.batch.jobs.db2synchronization.steps.documentationinstruction;

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

    @Value("${dbSync.shipment.documentation.documentationInstruction.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.shipment.documentation.documentationInstruction.cutOffHours}")
    private int cutOffHours;

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
        return new CursorReaderBuilder(db2Emf)
                .queryString("select d from DocumentationInstructionOld d where d.lastChange > " + DateTimeUtils.getCutOffHours(cutOffHours))
                .fetchSize(chunkSize)
                .build();
    }
}
