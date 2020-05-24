package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.functionalunit;

import com.hlag.fis.db.db2.model.FunctionalUnitOld;
import com.hlag.fis.db.mysql.model.FunctionalUnit;
import com.hlag.fis.db.mysql.repository.FunctionalUnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FunctionalUnitProcessor implements ItemProcessor<FunctionalUnitOld, FunctionalUnit> {

    private static final Logger logger = LoggerFactory.getLogger(FunctionalUnitProcessor.class);

    @Value("${functionalUnit.fullSync}")
    private boolean fullSync;

    private FunctionalUnitRepository functionalUnitRepository;

    @Autowired
    public FunctionalUnitProcessor(FunctionalUnitRepository functionalUnitRepository) {
        this.functionalUnitRepository = functionalUnitRepository;
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
    public FunctionalUnit process(FunctionalUnitOld functionalUnitOld) {
        logger.debug(format("Processing old functional unit  - {0}", functionalUnitOld));
        FunctionalUnit newFunctionalUnit;
        Optional<FunctionalUnit> oldFunctionalUnitOptional = functionalUnitRepository
                .findByEnvironmentAndIdentifier(functionalUnitOld.getId().getEnvironment(), functionalUnitOld.getId().getIdentifier());
        if (oldFunctionalUnitOptional.isPresent()) {
            if (!fullSync && oldFunctionalUnitOptional.get().getLastChange().equals(functionalUnitOld.getLastChange())) {
                // Nothing to do
                return null;
            }
            newFunctionalUnit = oldFunctionalUnitOptional.get();
        } else {
            newFunctionalUnit = new FunctionalUnit();
        }
        newFunctionalUnit.update(functionalUnitOld);

        return newFunctionalUnit;
    }
}