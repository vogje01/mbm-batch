package com.hlag.fis.batch.jobs.db2synchronization.steps.message;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class MessageReader {

    @Value("${dbSync.basis.message.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.message.cutOffDays}")
    private int cutOffDays;

    private EntityManagerFactory db2Emf;

    @Autowired
    public MessageReader(@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    public ItemStreamReader getReader() {
        return new CursorReaderBuilder(db2Emf)
                .queryString("select m from MessageOld m where m.lastChange > " + DateTimeUtils.getCutOff(cutOffDays))
                .timeout(300)
                .queryHint("WITH CS")
                .fetchSize(chunkSize).build();
    }
}
