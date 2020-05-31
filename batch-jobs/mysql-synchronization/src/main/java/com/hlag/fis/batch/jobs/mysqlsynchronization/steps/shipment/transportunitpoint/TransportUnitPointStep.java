package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.transportunitpoint;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.TransportUnitPointOld;
import com.hlag.fis.db.db2.repository.TransportUnitPointOldRepository;
import com.hlag.fis.db.mysql.model.TransportUnitPoint;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Transport unit point reader.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
public class TransportUnitPointStep {

    private static final String STEP_NAME = "Synchronize TransportUnitPoints";

    @Value("${transportUnitPoint.chunkSize}")
    private int chunkSize;

    @Value("${transportUnitPoint.cutOffHours}")
    private int cutOffHours;

    @Value("${transportUnitPoint.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<TransportUnitPointOld, TransportUnitPoint> stepBuilder;

    private TransportUnitPointOldRepository transportUnitPointOldRepository;

    private TransportUnitPointReader transportUnitPointReader;

    private TransportUnitPointProcessor transportUnitPointProcessor;

    private TransportUnitPointWriter transportUnitPointWriter;

    @Autowired
    public TransportUnitPointStep(
            BatchStepBuilder<TransportUnitPointOld, TransportUnitPoint> stepBuilder,
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

    @SuppressWarnings("unchecked")
    public Step synchronizeTransportUnitPoint() {
        long totalCount = fullSync ? transportUnitPointOldRepository.count() : transportUnitPointOldRepository.countByLastChange(DateTimeUtils.getCutOffHours(cutOffHours));
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
