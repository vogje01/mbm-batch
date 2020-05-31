package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.messagespecification;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * DB2 messageSpecification reader.
 * <p>
 * The timeout will be set to 900s.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Component
public class MessageSpecificationReader {

    @Value("${messageSpecification.chunkSize}")
    private int chunkSize;

    @Value("${messageSpecification.cutOffDays}")
    private int cutOffDays;

    @Value("${messageSpecification.fullSync}")
    private boolean fullSync;

    private EntityManagerFactory db2Emf;

    @Autowired
    public MessageSpecificationReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    @SuppressWarnings("rawtypes")
    public ItemStreamReader getReader() {
        String queryString = fullSync ? "select m from MessageSpecificationOld m" : "select m from MessageSpecificationOld m where m.lastChange > " + DateTimeUtils.getCutOff(cutOffDays);
        return new CursorReaderBuilder(db2Emf)
                .queryString(queryString)
                .timeout(900)
                .queryHint("WITH CS")
                .fetchSize(chunkSize).build();
    }
}
