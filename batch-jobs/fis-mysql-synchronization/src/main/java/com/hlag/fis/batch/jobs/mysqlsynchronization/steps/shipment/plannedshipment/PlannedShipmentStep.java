package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.plannedshipment;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.PlannedShipmentOld;
import com.hlag.fis.db.db2.repository.PlannedShipmentOldRepository;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlannedShipmentStep {

    private static final String STEP_NAME = "Synchronize PlannedShipments";

    @Value("${plannedShipment.chunkSize}")
    private int chunkSize;

    @Value("${plannedShipment.cutOffHours}")
    private int cutOffHours;

    @Value("${plannedShipment.fullSync}")
    private boolean fullSync;


    private BatchStepBuilder<PlannedShipmentOld, PlannedShipment> stepBuilder;

    private PlannedShipmentOldRepository plannedShipmentOldRepository;

    private PlannedShipmentReader plannedShipmentReader;

    private PlannedShipmentProcessor plannedShipmentProcessor;

    private PlannedShipmentWriter plannedShipmentWriter;

    @Autowired
    public PlannedShipmentStep(
            BatchStepBuilder<PlannedShipmentOld, PlannedShipment> stepBuilder,
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

    @SuppressWarnings("unchecked")
    public Step synchronizePlannedShipment() {
        long totalCount = fullSync ? plannedShipmentOldRepository.count() : plannedShipmentOldRepository.countByLastChange(DateTimeUtils.getCutOffHours(cutOffHours));
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
