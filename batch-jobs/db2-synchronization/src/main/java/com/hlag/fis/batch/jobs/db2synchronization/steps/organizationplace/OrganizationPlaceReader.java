package com.hlag.fis.batch.jobs.db2synchronization.steps.organizationplace;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Organizational place reader.
 * <p>
 * For a full synchronization use: select o from OrganizationPlaceOld o where o.neverExpires = 'Y' or o.expirationDate &gt; CURRENT_DATE, otherwise
 * a lot of old organization places are synchronized.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.1
 */
@Component
public class OrganizationPlaceReader {

    @Value("${dbSync.organization.organizationPlace.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.organization.organizationPlace.cutOffDays}")
    private int cutOffDays;

    private EntityManagerFactory db2Emf;

    @Autowired
    public OrganizationPlaceReader(@Qualifier("db2ProdEntityManagerFactory") EntityManagerFactory db2Emf) {
        this.db2Emf = db2Emf;
    }

    public ItemStreamReader getReader() {
        return new CursorReaderBuilder(db2Emf)
                .queryString("select o from OrganizationPlaceOld o where o.lastChange > " + DateTimeUtils.getCutOff(cutOffDays))
                .timeout(300)
                .queryHint("WITH CS")
                .fetchSize(chunkSize)
                .build();
    }

}
