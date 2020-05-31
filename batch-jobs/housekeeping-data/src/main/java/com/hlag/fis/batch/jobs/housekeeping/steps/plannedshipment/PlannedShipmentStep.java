package com.hlag.fis.batch.jobs.housekeeping.steps.plannedshipment;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import com.hlag.fis.db.mysql.repository.PlannedShipmentRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Planned shipment housekeeping step.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Component
public class PlannedShipmentStep {

    private static final String STEP_NAME = "Planned Shipments";

    @Value("${shipment.booking.plannedShipment.houseKeepingDays}")
    private int houseKeepingDays;

    @Value("${shipment.booking.plannedShipment.chunkSize}")
    private int chunkSize;

    private BatchStepBuilder<PlannedShipment, PlannedShipment> stepBuilder;

    private PlannedShipmentRepository plannedShipmentRepository;

    private PlannedShipmentReader plannedShipmentReader;

    private PlannedShipmentProcessor plannedShipmentProcessor;

    private PlannedShipmentWriter plannedShipmentWriter;

    @Autowired
    @SuppressWarnings("squid:S00107")
    public PlannedShipmentStep(
            BatchStepBuilder<PlannedShipment, PlannedShipment> stepBuilder,
            PlannedShipmentRepository plannedShipmentRepository,
            PlannedShipmentReader plannedShipmentReader,
            PlannedShipmentProcessor plannedShipmentProcessor,
            PlannedShipmentWriter plannedShipmentWriter) {
        this.stepBuilder = stepBuilder;
        this.plannedShipmentRepository = plannedShipmentRepository;
        this.plannedShipmentReader = plannedShipmentReader;
        this.plannedShipmentProcessor = plannedShipmentProcessor;
        this.plannedShipmentWriter = plannedShipmentWriter;
    }

    @SuppressWarnings("unchecked")
    public Step houseKeepingPlannedShipments() {
        long totalCount = plannedShipmentRepository.countByLastChange(DateTimeUtils.getCutOff(houseKeepingDays));
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
