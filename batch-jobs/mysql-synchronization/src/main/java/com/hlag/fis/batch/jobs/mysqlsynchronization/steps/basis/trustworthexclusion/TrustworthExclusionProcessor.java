package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.trustworthexclusion;

import com.hlag.fis.db.db2.model.TrustworthExclusionOld;
import com.hlag.fis.db.mysql.model.FunctionalUnit;
import com.hlag.fis.db.mysql.model.TrustworthExclusion;
import com.hlag.fis.db.mysql.repository.FunctionalUnitRepository;
import com.hlag.fis.db.mysql.repository.TrustworthExclusionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Trust worth exclusion entity.
 *
 * @author Jens.Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
public class TrustworthExclusionProcessor implements ItemProcessor<TrustworthExclusionOld, TrustworthExclusion> {

    private static final Logger logger = LoggerFactory.getLogger(TrustworthExclusionProcessor.class);

    @Value("${trustworthExclusion.fullSync}")
    private boolean fullSync;

    private FunctionalUnitRepository functionalUnitRepository;

    private TrustworthExclusionRepository trustworthExclusionRepository;

    @Autowired
    public TrustworthExclusionProcessor(TrustworthExclusionRepository trustworthExclusionRepository, FunctionalUnitRepository functionalUnitRepository) {
        this.trustworthExclusionRepository = trustworthExclusionRepository;
        this.functionalUnitRepository = functionalUnitRepository;
    }

    /**
     * Item processor for the trust worth exclusion entity.
     *
     * @param trustworthExclusionOld old DB2 trust worth exclusion entity.
     * @return MySQL trust worth exclusion entity.
     */
    @Override
    public TrustworthExclusion process(TrustworthExclusionOld trustworthExclusionOld) {
        TrustworthExclusion newTrustworthExclusion;
        Optional<TrustworthExclusion> oldTrustworthExclusionOptional = trustworthExclusionRepository.findByClassAndEnvironmentAndIdentifier(trustworthExclusionOld.getId().getTrustWorthClass(), trustworthExclusionOld.getId().getFunctionalUnitEnvironment(), trustworthExclusionOld.getId().getFunctionalUnitIdentifier());
        if (oldTrustworthExclusionOptional.isPresent()) {
            if (!fullSync && oldTrustworthExclusionOptional.get().getLastChange().equals(trustworthExclusionOld.getLastChange())) {
                // Nothing to do
                return null;
            }
            newTrustworthExclusion = oldTrustworthExclusionOptional.get();
        } else {
            newTrustworthExclusion = new TrustworthExclusion();
        }
        newTrustworthExclusion.update(trustworthExclusionOld);

        // Get constraints
        getFunctionalUnitConstraint(trustworthExclusionOld, newTrustworthExclusion);

        return newTrustworthExclusion;
    }

    /**
     * Add the unidirectional one to one relationship from trust worth exclusion to functional unit. The relationship
     * is mapped by the functional_unit_id uuid in the trust worth exclusion entity.
     *
     * @param trustworthExclusionOld odl DB2 trust worth exclusion.
     * @param trustworthExclusion    trust worth exclusion.
     */
    private void getFunctionalUnitConstraint(TrustworthExclusionOld trustworthExclusionOld, TrustworthExclusion trustworthExclusion) {
        Optional<FunctionalUnit> functionalUnitOptional = functionalUnitRepository.findByEnvironmentAndIdentifier(trustworthExclusionOld.getId().getFunctionalUnitEnvironment(), trustworthExclusionOld.getId().getFunctionalUnitIdentifier());
        functionalUnitOptional.ifPresent(f -> {
            if (trustworthExclusion.getFunctionalUnit() == null || !trustworthExclusion.getFunctionalUnit().getId().equals(functionalUnitOptional.get().getId())) {
                trustworthExclusion.setFunctionalUnit(functionalUnitOptional.get());
            }
        });
    }
}