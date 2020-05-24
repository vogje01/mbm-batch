package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.functionalunit;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.FunctionalUnitOld;
import com.hlag.fis.db.db2.repository.FunctionalUnitOldRepository;
import com.hlag.fis.db.mysql.model.FunctionalUnit;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FunctionalUnitStep {

    private static final String STEP_NAME = "Synchronize Functional Units";

    @Value("${functionalUnit.chunkSize}")
    private int chunkSize;

    @Value("${functionalUnit.cutOffDays}")
    private int cutOffDays;

    @Value("${users.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<FunctionalUnitOld, FunctionalUnit> stepBuilder;

    private FunctionalUnitOldRepository functionalUnitOldRepository;

    private FunctionalUnitReader functionalUnitReader;

    private FunctionalUnitProcessor functionalUnitProcessor;

    private FunctionalUnitWriter functionalUnitWriter;

    @Autowired
    public FunctionalUnitStep(
            BatchStepBuilder<FunctionalUnitOld, FunctionalUnit> stepBuilder,
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

    @SuppressWarnings("unchecked")
    public Step synchronizeFunctionalUnit() {
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
