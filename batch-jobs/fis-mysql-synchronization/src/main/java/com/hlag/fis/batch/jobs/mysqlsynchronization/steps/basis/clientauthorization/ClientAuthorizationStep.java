package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.clientauthorization;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.ClientAuthorizationOld;
import com.hlag.fis.db.db2.repository.ClientAuthorizationOldRepository;
import com.hlag.fis.db.mysql.model.ClientAuthorization;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientAuthorizationStep {

    private static final String STEP_NAME = "Synchronize Clients Authorizations";

    @Value("${clientAuthorization.chunkSize}")
    private int chunkSize;

    @Value("${clientAuthorization.cutOffDays}")
    private int cutOffDays;

    @Value("${users.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<ClientAuthorizationOld, ClientAuthorization> stepBuilder;

    private ClientAuthorizationOldRepository clientAuthorizationOldRepository;

    private ClientAuthorizationReader clientAuthorizationReader;

    private ClientAuthorizationProcessor clientAuthorizationProcessor;

    private ClientAuthorizationWriter clientAuthorizationWriter;

    @Autowired
    public ClientAuthorizationStep(
            BatchStepBuilder<ClientAuthorizationOld, ClientAuthorization> stepBuilder,
            ClientAuthorizationOldRepository clientAuthorizationOldRepository,
            ClientAuthorizationReader clientAuthorizationReader,
            ClientAuthorizationProcessor clientAuthorizationProcessor,
            ClientAuthorizationWriter clientAuthorizationWriter) {
        this.stepBuilder = stepBuilder;
        this.clientAuthorizationOldRepository = clientAuthorizationOldRepository;
        this.clientAuthorizationReader = clientAuthorizationReader;
        this.clientAuthorizationProcessor = clientAuthorizationProcessor;
        this.clientAuthorizationWriter = clientAuthorizationWriter;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeClientAuthorization() {
        long totalCount = fullSync ? clientAuthorizationOldRepository.count() : clientAuthorizationOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(clientAuthorizationReader.getReader())
                .processor(clientAuthorizationProcessor)
                .writer(clientAuthorizationWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
