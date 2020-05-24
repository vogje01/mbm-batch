package com.hlag.fis.batch.jobs.db2synchronization.steps.functionalunit;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.FunctionalUnitOld;
import com.hlag.fis.db.db2.repository.FunctionalUnitOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FunctionalUnitStep {

    private static final String STEP_NAME = "Synchronize Functional Units";

    @Value("${dbSync.basis.functionalUnit.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.functionalUnit.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.functionalUnit.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.functionalUnit.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<FunctionalUnitOld, FunctionalUnitOld> stepBuilder;

    private FunctionalUnitOldRepository functionalUnitOldRepository;

    private FunctionalUnitReader functionalUnitReader;

    private FunctionalUnitProcessor functionalUnitProcessor;

    private FunctionalUnitWriter functionalUnitWriter;

    @Autowired
    public FunctionalUnitStep(
            BatchStepBuilder<FunctionalUnitOld, FunctionalUnitOld> stepBuilder,
            FunctionalUnitOldRepository functionalUnitOldRepository,
            FunctionalUnitReader functionalUnitReader,
            FunctionalUnitProcessor functionalUnitProcessor,
            FunctionalUnitWriter functionalUnitWriter) {
        this.stepBuilder = stepBuilder;
        this.functionalUnitOldRepository = functionalUnitOldRepository;
        this.functionalUnitReader = functionalUnitReader;
        this.functionalUnitProcessor = functionalUnitProcessor;
        this.functionalUnitWriter = functionalUnitWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeFunctionUnit() {
        long totalCount = fullSync ? functionalUnitOldRepository.count() : functionalUnitOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(functionalUnitReader.getReader())
                .processor(functionalUnitProcessor)
                .writer(functionalUnitWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
