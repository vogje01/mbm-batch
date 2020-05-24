package com.hlag.fis.batch.jobs.db2synchronization.steps.geohierarchy;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.GeoHierarchyOld;
import com.hlag.fis.db.db2.repository.GeoHierarchyOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoHierarchyStep {

    private static final String STEP_NAME = "Synchronize Geo Hierarchies";

    @Value("${dbSync.basis.geoHierarchy.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.geoHierarchy.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.geoHierarchy.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.geoHierarchy.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<GeoHierarchyOld, GeoHierarchyOld> stepBuilder;

    private GeoHierarchyOldRepository geoHierarchyOldRepository;

    private GeoHierarchyReader geoHierarchyReader;

    private GeoHierarchyProcessor geoHierarchyProcessor;

    private GeoHierarchyWriter geoHierarchyWriter;

    @Autowired
    public GeoHierarchyStep(
            BatchStepBuilder<GeoHierarchyOld, GeoHierarchyOld> stepBuilder,
            GeoHierarchyOldRepository geoHierarchyOldRepository,
            GeoHierarchyReader geoHierarchyReader,
            GeoHierarchyProcessor geoHierarchyProcessor,
            GeoHierarchyWriter geoHierarchyWriter) {
        this.stepBuilder = stepBuilder;
        this.geoHierarchyOldRepository = geoHierarchyOldRepository;
        this.geoHierarchyReader = geoHierarchyReader;
        this.geoHierarchyProcessor = geoHierarchyProcessor;
        this.geoHierarchyWriter = geoHierarchyWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeGeoHierarchy() {
        long totalCount = fullSync ? geoHierarchyOldRepository.count() : geoHierarchyOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(geoHierarchyReader.getReader())
                .processor(geoHierarchyProcessor)
                .writer(geoHierarchyWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
