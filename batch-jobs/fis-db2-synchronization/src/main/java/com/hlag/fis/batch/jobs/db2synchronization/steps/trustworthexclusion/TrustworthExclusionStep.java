package com.hlag.fis.batch.jobs.db2synchronization.steps.trustworthexclusion;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.TrustworthExclusionOld;
import com.hlag.fis.db.db2.repository.TrustworthExclusionOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrustworthExclusionStep {

    private static final String STEP_NAME = "Synchronize Trustworth Exclusion";

    @Value("${dbSync.basis.trustworthExclusion.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.trustworthExclusion.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.trustworthExclusion.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.trustworthExclusion.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<TrustworthExclusionOld, TrustworthExclusionOld> stepBuilder;

    private TrustworthExclusionOldRepository trustworthExclusionOldRepository;

    private TrustworthExclusionReader trustworthExclusionReader;

    private TrustworthExclusionProcessor trustworthExclusionProcessor;

    private TrustworthExclusionWriter trustworthExclusionWriter;

    @Autowired
    public TrustworthExclusionStep(
        BatchStepBuilder<TrustworthExclusionOld, TrustworthExclusionOld> stepBuilder,
        TrustworthExclusionOldRepository trustworthExclusionOldRepository,
        TrustworthExclusionReader trustworthExclusionReader,
        TrustworthExclusionProcessor trustworthExclusionProcessor,
        TrustworthExclusionWriter trustworthExclusionWriter) {
        this.stepBuilder = stepBuilder;
        this.trustworthExclusionOldRepository = trustworthExclusionOldRepository;
        this.trustworthExclusionReader = trustworthExclusionReader;
        this.trustworthExclusionProcessor = trustworthExclusionProcessor;
        this.trustworthExclusionWriter = trustworthExclusionWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeTrustworthExclusion() {
        long totalCount = fullSync ? trustworthExclusionOldRepository.count() : trustworthExclusionOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
            .name(STEP_NAME)
            .chunkSize(chunkSize)
            .reader(trustworthExclusionReader.getReader())
            .processor(trustworthExclusionProcessor)
            .writer(trustworthExclusionWriter.getWriter())
            .total(totalCount)
            .build();
    }
}
