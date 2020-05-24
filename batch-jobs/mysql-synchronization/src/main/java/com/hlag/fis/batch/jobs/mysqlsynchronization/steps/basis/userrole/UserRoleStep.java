package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.userrole;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.UserRoleOld;
import com.hlag.fis.db.db2.repository.UserRoleOldRepository;
import com.hlag.fis.db.mysql.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import static java.text.MessageFormat.format;

@Configuration
public class UserRoleStep {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleStep.class);

    private static final String STEP_NAME = "Synchronize UserRoles";

    @Value("${userRole.chunkSize}")
    private int chunkSize;

    @Value("${userRole.cutOffDays}")
    private int cutOffDays;

    @Value("${userRole.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<UserRoleOld, UserRole> stepBuilder;

    private UserRoleOldRepository userRoleOldRepository;

    private UserRoleReader userRoleReader;

    private UserRoleProcessor userRoleProcessor;

    private UserRoleWriter userRoleWriter;

    @Autowired
    public UserRoleStep(
            BatchStepBuilder<UserRoleOld, UserRole> stepBuilder,
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

    @SuppressWarnings("unchecked")
    public Step synchronizeUserRole() {
        long totalCount = fullSync ? userRoleOldRepository.count() : userRoleOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        logger.info(format("Starting step - name: {0} totalCount: {1} cutOffDay: {2}", STEP_NAME, totalCount, cutOffDays));
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
