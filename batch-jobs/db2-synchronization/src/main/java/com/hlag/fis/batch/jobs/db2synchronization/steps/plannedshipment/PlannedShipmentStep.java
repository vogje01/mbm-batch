package com.hlag.fis.batch.jobs.db2synchronization.steps.plannedshipment;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.PlannedShipmentOld;
import com.hlag.fis.db.db2.repository.PlannedShipmentOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlannedShipmentStep {

    private static final String STEP_NAME = "Synchronize PlannedShipments";

    @Value("${dbSync.basis.plannedShipment.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.plannedShipment.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.plannedShipment.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.plannedShipment.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<PlannedShipmentOld, PlannedShipmentOld> stepBuilder;

    private PlannedShipmentOldRepository plannedShipmentOldRepository;

    private PlannedShipmentReader plannedShipmentReader;

    private PlannedShipmentProcessor plannedShipmentProcessor;

    private PlannedShipmentWriter plannedShipmentWriter;

    @Autowired
    public PlannedShipmentStep(
            BatchStepBuilder<PlannedShipmentOld, PlannedShipmentOld> stepBuilder,
            PlannedShipmentOldRepository plannedShipmentOldRepository,
            PlannedShipmentReader plannedShipmentReader,
            PlannedShipmentProcessor plannedShipmentProcessor,
            PlannedShipmentWriter plannedShipmentWriter) {
        this.stepBuilder = stepBuilder;
        this.plannedShipmentOldRepository = plannedShipmentOldRepository;
        this.plannedShipmentReader = plannedShipmentReader;
        this.plannedShipmentProcessor = plannedShipmentProcessor;
        this.plannedShipmentWriter = plannedShipmentWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizePlannedShipments() {
        long totalCount = fullSync ? plannedShipmentOldRepository.count() : plannedShipmentOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(plannedShipmentReader.getReader())
                .processor(plannedShipmentProcessor)
                .writer(plannedShipmentWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
