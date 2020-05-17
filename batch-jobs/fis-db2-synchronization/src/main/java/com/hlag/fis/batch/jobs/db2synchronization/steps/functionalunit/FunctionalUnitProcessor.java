package com.hlag.fis.batch.jobs.db2synchronization.steps.functionalunit;

import com.hlag.fis.db.db2.model.FunctionalUnitOld;
import com.hlag.fis.db.db2.repository.FunctionalUnitOldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FunctionalUnitProcessor implements ItemProcessor<FunctionalUnitOld, FunctionalUnitOld> {

    private static final Logger logger = LoggerFactory.getLogger(FunctionalUnitProcessor.class);

    @Value("${dbSync.basis.functionalUnit.fullSync}")
    private boolean fullSync;

    private FunctionalUnitOldRepository functionalUnitOldRepository;

    @Autowired
    public FunctionalUnitProcessor(FunctionalUnitOldRepository functionalUnitOldRepository) {
        this.functionalUnitOldRepository = functionalUnitOldRepository;
    }

    /**
     * Item processor for the functional unit model.
     * <p>
     * This will create a new MySQL functional unit model.
     *
     * @param functionalUnitOld old DB2 user .
     * @return full developed MySQL functional unit model.
     */
    @Override
    public FunctionalUnitOld process(FunctionalUnitOld functionalUnitOld) {
        logger.debug("Processing old functional unit  - " + functionalUnitOld.toString());
        /*FunctionalUnit newFunctionalUnit;
        FunctionalUnit oldFunctionalUnit = functionalUnitRepository.findByEnvironmentAndIdentifier(functionalUnitOld.getId().getEnvironment(),
          functionalUnitOld.getId().getIdentifier());
        if (oldFunctionalUnit != null) {
            newFunctionalUnit = new FunctionalUnit(functionalUnitOld, oldFunctionalUnit.getId());
            if (!fullSync && !oldFunctionalUnit.getLastChange().equals(functionalUnitOld.getLastChange())) {
                // Nothing to do
                return null;
            }
        } else {
            newFunctionalUnit = new FunctionalUnit(functionalUnitOld);
        }*/
        return functionalUnitOld;
    }
}