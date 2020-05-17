package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization.location;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.LocationOld;
import com.hlag.fis.db.db2.repository.LocationOldRepository;
import com.hlag.fis.db.mysql.model.Location;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocationStep {

    private static final String STEP_NAME = "Synchronize Locations";

    @Value("${location.chunkSize}")
    private int chunkSize;

    @Value("${location.cutOffDays}")
    private int cutOffDays;

    @Value("${location.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<LocationOld, Location> stepBuilder;

    private LocationOldRepository locationOldRepository;

    private LocationReader locationReader;

    private LocationProcessor locationProcessor;

    private LocationWriter locationWriter;

    @Autowired
    public LocationStep(
            BatchStepBuilder<LocationOld, Location> stepBuilder,
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
