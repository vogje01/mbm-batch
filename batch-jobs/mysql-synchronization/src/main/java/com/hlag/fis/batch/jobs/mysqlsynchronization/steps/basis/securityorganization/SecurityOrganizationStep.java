package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.securityorganization;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.SecurityOrganizationOld;
import com.hlag.fis.db.db2.repository.SecurityOrganizationOldRepository;
import com.hlag.fis.db.mysql.model.SecurityOrganization;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityOrganizationStep {

    private static final String STEP_NAME = "Synchronize Security Organizations";

    @Value("${securityOrganization.chunkSize}")
    private int chunkSize;

    @Value("${securityOrganization.cutOffDays}")
    private int cutOffDays;

    @Value("${securityOrganization.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<SecurityOrganizationOld, SecurityOrganization> stepBuilder;

    private SecurityOrganizationOldRepository securityOrganizationOldRepository;

    private SecurityOrganizationReader securityOrganizationReader;

    private SecurityOrganizationProcessor securityOrganizationProcessor;

    private SecurityOrganizationWriter securityOrganizationWriter;

    @Autowired
    public SecurityOrganizationStep(
            BatchStepBuilder<SecurityOrganizationOld, SecurityOrganization> stepBuilder,
            SecurityOrganizationOldRepository securityOrganizationOldRepository,
            SecurityOrganizationReader securityOrganizationReader,
            SecurityOrganizationProcessor securityOrganizationProcessor,
            SecurityOrganizationWriter securityOrganizationWriter) {
        this.stepBuilder = stepBuilder;
        this.securityOrganizationOldRepository = securityOrganizationOldRepository;
        this.securityOrganizationReader = securityOrganizationReader;
        this.securityOrganizationProcessor = securityOrganizationProcessor;
        this.securityOrganizationWriter = securityOrganizationWriter;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeSecurityOrganization() {
        long totalCount = fullSync ? securityOrganizationOldRepository.count() : securityOrganizationOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(securityOrganizationReader.getReader())
                .processor(securityOrganizationProcessor)
                .writer(securityOrganizationWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
