package com.hlag.fis.batch.jobs.db2synchronization.steps.users;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.UsersOld;
import com.hlag.fis.db.db2.repository.UsersOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersStep {

    private static final String STEP_NAME = "Synchronize Users";

    @Value("${dbSync.basis.users.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.users.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.users.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.users.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<UsersOld, UsersOld> stepBuilder;

    private UsersOldRepository usersOldRepository;

    private UsersReader usersReader;

    private UsersProcessor usersProcessor;

    private UsersWriter usersWriter;

    @Autowired
    public UsersStep(
        BatchStepBuilder<UsersOld, UsersOld> stepBuilder,
        UsersOldRepository usersOldRepository,
        UsersReader usersReader,
        UsersProcessor usersProcessor,
        UsersWriter usersWriter) {
        this.stepBuilder = stepBuilder;
        this.usersOldRepository = usersOldRepository;
        this.usersReader = usersReader;
        this.usersProcessor = usersProcessor;
        this.usersWriter = usersWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeUsers() {
        long totalCount = fullSync ? usersOldRepository.count() : usersOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
            .name(STEP_NAME)
            .chunkSize(chunkSize)
            .reader(usersReader.getReader())
            .processor(usersProcessor)
            .writer(usersWriter.getWriter())
            .total(totalCount)
            .build();
    }
}

