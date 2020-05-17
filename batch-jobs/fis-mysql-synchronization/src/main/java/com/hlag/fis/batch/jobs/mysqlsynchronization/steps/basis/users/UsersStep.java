package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.users;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.UsersOld;
import com.hlag.fis.db.db2.repository.UsersOldRepository;
import com.hlag.fis.db.mysql.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import static java.text.MessageFormat.format;

@Configuration
public class UsersStep {

    private static final Logger logger = LoggerFactory.getLogger(UsersStep.class);

    private static final String STEP_NAME = "Synchronize Users";

    @Value("${users.chunkSize}")
    private int chunkSize;

    @Value("${users.cutOffDays}")
    private int cutOffDays;

    @Value("${users.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<UsersOld, Users> stepBuilder;

    private UsersOldRepository usersOldRepository;

    private UsersReader userSReader;

    private UsersProcessor userSProcessor;

    private UsersWriter userSWriter;

    @Autowired
    public UsersStep(
            BatchStepBuilder<UsersOld, Users> stepBuilder,
            UsersOldRepository usersOldRepository,
            UsersReader userSReader,
            UsersProcessor userSProcessor,
            UsersWriter userSWriter) {
        this.stepBuilder = stepBuilder;
        this.usersOldRepository = usersOldRepository;
        this.userSReader = userSReader;
        this.userSProcessor = userSProcessor;
        this.userSWriter = userSWriter;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeUsers() {
        long totalCount = fullSync ? usersOldRepository.count() : usersOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        logger.info(format("Starting step - name: {0} totalCount: {1} cutOffDay: {2}", STEP_NAME, totalCount, cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(userSReader.getReader())
                .processor(userSProcessor)
                .writer(userSWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
