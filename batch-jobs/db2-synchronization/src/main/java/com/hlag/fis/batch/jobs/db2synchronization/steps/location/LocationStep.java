package com.hlag.fis.batch.jobs.db2synchronization.steps.location;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.LocationOld;
import com.hlag.fis.db.db2.repository.LocationOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationStep {

    private static final String STEP_NAME = "Synchronize Locations";

    @Value("${dbSync.basis.location.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.location.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.location.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.location.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<LocationOld, LocationOld> stepBuilder;

    private LocationOldRepository locationOldRepository;

    private LocationReader locationReader;

    private LocationProcessor locationProcessor;

    private LocationWriter locationWriter;

    @Autowired
    public LocationStep(
            BatchStepBuilder<LocationOld, LocationOld> stepBuilder,
            LocationOldRepository locationOldRepository,
            LocationReader locationReader,
            LocationProcessor locationProcessor,
            LocationWriter locationWriter) {
        this.stepBuilder = stepBuilder;
        this.locationOldRepository = locationOldRepository;
        this.locationReader = locationReader;
        this.locationProcessor = locationProcessor;
        this.locationWriter = locationWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeLocation() {
        long totalCount = fullSync ? locationOldRepository.count() : locationOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(locationReader.getReader())
                .processor(locationProcessor)
                .writer(locationWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
