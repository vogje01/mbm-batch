package com.hlag.fis.batch.jobs.db2synchronization.steps.transportunitpoint;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.TransportUnitPointOld;
import com.hlag.fis.db.db2.repository.TransportUnitPointOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransportUnitPointStep {

    private static final String STEP_NAME = "Synchronize Transport Unit Points";

    @Value("${dbSync.basis.transportUnitPoint.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.transportUnitPoint.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.transportUnitPoint.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.transportUnitPoint.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<TransportUnitPointOld, TransportUnitPointOld> stepBuilder;

    private TransportUnitPointOldRepository transportUnitPointOldRepository;

    private TransportUnitPointReader transportUnitPointReader;

    private TransportUnitPointProcessor transportUnitPointProcessor;

    private TransportUnitPointWriter transportUnitPointWriter;

    @Autowired
    public TransportUnitPointStep(
            BatchStepBuilder<TransportUnitPointOld, TransportUnitPointOld> stepBuilder,
            TransportUnitPointOldRepository transportUnitPointOldRepository,
            TransportUnitPointReader transportUnitPointReader,
            TransportUnitPointProcessor transportUnitPointProcessor,
            TransportUnitPointWriter transportUnitPointWriter) {
        this.stepBuilder = stepBuilder;
        this.transportUnitPointOldRepository = transportUnitPointOldRepository;
        this.transportUnitPointReader = transportUnitPointReader;
        this.transportUnitPointProcessor = transportUnitPointProcessor;
        this.transportUnitPointWriter = transportUnitPointWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeTransportUnitPoints() {
        long totalCount = fullSync ? transportUnitPointOldRepository.count() : transportUnitPointOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(transportUnitPointReader.getReader())
                .processor(transportUnitPointProcessor)
                .writer(transportUnitPointWriter.getWriter())
                .total(totalCount)
                .build();
    }
}

