package com.hlag.fis.batch.jobs.db2synchronization.steps.userrole;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.UserRoleOld;
import com.hlag.fis.db.db2.repository.UserRoleOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRoleStep {

    private static final String STEP_NAME = "Synchronize User Roles";

    @Value("${dbSync.basis.userRole.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.userRole.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.userRole.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.userRole.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<UserRoleOld, UserRoleOld> stepBuilder;

    private UserRoleOldRepository userRoleOldRepository;

    private UserRoleReader userRoleReader;

    private UserRoleProcessor userRoleProcessor;

    private UserRoleWriter userRoleWriter;

    @Autowired
    public UserRoleStep(
        BatchStepBuilder<UserRoleOld, UserRoleOld> stepBuilder,
        UserRoleOldRepository userRoleOldRepository,
        UserRoleReader userRoleReader,
        UserRoleProcessor userRoleProcessor,
        UserRoleWriter userRoleWriter) {
        this.stepBuilder = stepBuilder;
        this.userRoleOldRepository = userRoleOldRepository;
        this.userRoleReader = userRoleReader;
        this.userRoleProcessor = userRoleProcessor;
        this.userRoleWriter = userRoleWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeUserRole() {
        long totalCount = fullSync ? userRoleOldRepository.count() : userRoleOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
            .name(STEP_NAME)
            .chunkSize(chunkSize)
            .reader(userRoleReader.getReader())
            .processor(userRoleProcessor)
            .writer(userRoleWriter.getWriter())
            .total(totalCount)
            .build();
    }
}

