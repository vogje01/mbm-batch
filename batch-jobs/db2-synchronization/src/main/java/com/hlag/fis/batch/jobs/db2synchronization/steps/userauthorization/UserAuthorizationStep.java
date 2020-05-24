package com.hlag.fis.batch.jobs.db2synchronization.steps.userauthorization;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.UserAuthorizationOld;
import com.hlag.fis.db.db2.repository.UserAuthorizationOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAuthorizationStep {

    private static final String STEP_NAME = "Synchronize Users Authorizations";

    @Value("${dbSync.basis.userAuthorization.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.userAuthorization.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.user.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.user.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<UserAuthorizationOld, UserAuthorizationOld> stepBuilder;

    private UserAuthorizationOldRepository userAuthorizationOldRepository;

    private UserAuthorizationReader userAuthorizationReader;

    private UserAuthorizationProcessor userAuthorizationProcessor;

    private UserAuthorizationWriter userAuthorizationWriter;

    @Autowired
    public UserAuthorizationStep(
            BatchStepBuilder<UserAuthorizationOld, UserAuthorizationOld> stepBuilder,
            UserAuthorizationOldRepository userAuthorizationOldRepository,
            UserAuthorizationReader userAuthorizationReader,
            UserAuthorizationProcessor userAuthorizationProcessor,
            UserAuthorizationWriter userAuthorizationWriter) {
        this.stepBuilder = stepBuilder;
        this.userAuthorizationOldRepository = userAuthorizationOldRepository;
        this.userAuthorizationReader = userAuthorizationReader;
        this.userAuthorizationProcessor = userAuthorizationProcessor;
        this.userAuthorizationWriter = userAuthorizationWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeUserAuthorization() {
        long totalCount = fullSync ? userAuthorizationOldRepository.count() : userAuthorizationOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(userAuthorizationReader.getReader())
                .processor(userAuthorizationProcessor)
                .writer(userAuthorizationWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
