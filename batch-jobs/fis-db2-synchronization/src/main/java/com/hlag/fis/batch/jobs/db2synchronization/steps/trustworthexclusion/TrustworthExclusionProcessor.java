package com.hlag.fis.batch.jobs.db2synchronization.steps.trustworthexclusion;

import com.hlag.fis.db.db2.model.TrustworthExclusionOld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TrustworthExclusionProcessor implements ItemProcessor<TrustworthExclusionOld, TrustworthExclusionOld> {

    private static final Logger logger = LoggerFactory.getLogger(TrustworthExclusionProcessor.class);

    @Value("${dbSync.basis.trustworthExclusion.fullSync}")
    private boolean fullSync;

    /**
     * Item processor for the user authorization model.
     * <p>
     * This will create a new MySQL user authorization model.
     *
     * @param trustworthExclusionOld old DB2 user authorization.
     * @return full developed MySQL user authorization model.
     */
    @Override
    public TrustworthExclusionOld process(TrustworthExclusionOld trustworthExclusionOld) {
        logger.debug("Processing old trustworth exclusion - " + trustworthExclusionOld.toString());
        return trustworthExclusionOld;
    }
}