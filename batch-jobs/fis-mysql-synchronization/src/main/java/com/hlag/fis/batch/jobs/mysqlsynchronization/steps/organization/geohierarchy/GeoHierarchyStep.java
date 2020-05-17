package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization.geohierarchy;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.GeoHierarchyOld;
import com.hlag.fis.db.db2.repository.GeoHierarchyOldRepository;
import com.hlag.fis.db.mysql.model.GeoHierarchy;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeoHierarchyStep {

    private static final String STEP_NAME = "Synchronize Geo Hierarchies";

    @Value("${geoHierarchy.chunkSize}")
    private int chunkSize;

    @Value("${geoHierarchy.cutOffDays}")
    private int cutOffDays;

    @Value("${geoHierarchy.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<GeoHierarchyOld, GeoHierarchy> stepBuilder;

    private GeoHierarchyOldRepository geoHierarchyOldRepository;

    private GeoHierarchyReader geoHierarchyReader;

    private GeoHierarchyProcessor geoHierarchyProcessor;

    private GeoHierarchyWriter geoHierarchyWriter;

    @Autowired
    public GeoHierarchyStep(
            BatchStepBuilder<GeoHierarchyOld, GeoHierarchy> stepBuilder,
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
