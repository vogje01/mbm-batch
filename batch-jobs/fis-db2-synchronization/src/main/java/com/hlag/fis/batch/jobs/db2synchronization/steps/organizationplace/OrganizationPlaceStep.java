package com.hlag.fis.batch.jobs.db2synchronization.steps.organizationplace;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.OrganizationPlaceOld;
import com.hlag.fis.db.db2.repository.OrganizationPlaceOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationPlaceStep {

    private static final String STEP_NAME = "Synchronize OrganizationPlaces";

    @Value("${dbSync.basis.organizationPlace.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.organizationPlace.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.organizationPlace.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.organizationPlace.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<OrganizationPlaceOld, OrganizationPlaceOld> stepBuilder;

    private OrganizationPlaceOldRepository organizationPlaceOldRepository;

    private OrganizationPlaceReader organizationPlaceReader;

    private OrganizationPlaceProcessor organizationPlaceProcessor;

    private OrganizationPlaceWriter organizationPlaceWriter;

    @Autowired
    public OrganizationPlaceStep(
        BatchStepBuilder<OrganizationPlaceOld, OrganizationPlaceOld> stepBuilder,
        OrganizationPlaceOldRepository organizationPlaceOldRepository,
        OrganizationPlaceReader organizationPlaceReader,
        OrganizationPlaceProcessor organizationPlaceProcessor,
        OrganizationPlaceWriter organizationPlaceWriter) {
        this.stepBuilder = stepBuilder;
        this.organizationPlaceOldRepository = organizationPlaceOldRepository;
        this.organizationPlaceReader = organizationPlaceReader;
        this.organizationPlaceProcessor = organizationPlaceProcessor;
        this.organizationPlaceWriter = organizationPlaceWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeOrganizationPlace() {
        long totalCount = fullSync ? organizationPlaceOldRepository.count() : organizationPlaceOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
            .name(STEP_NAME)
            .chunkSize(chunkSize)
            .reader(organizationPlaceReader.getReader())
            .processor(organizationPlaceProcessor)
            .writer(organizationPlaceWriter.getWriter())
            .total(totalCount)
            .build();
    }
}
