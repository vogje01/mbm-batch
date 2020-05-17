package com.hlag.fis.batch.jobs.db2synchronization.steps.clientauthorization;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.ClientAuthorizationOld;
import com.hlag.fis.db.db2.repository.ClientAuthorizationOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientAuthorizationStep {

    private static final String STEP_NAME = "Synchronize Clients Authorizations";

    @Value("${dbSync.basis.clientAuthorization.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.clientAuthorization.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.client.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.client.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<ClientAuthorizationOld, ClientAuthorizationOld> stepBuilder;

    private ClientAuthorizationOldRepository clientAuthorizationOldRepository;

    private ClientAuthorizationReader clientAuthorizationReader;

    private ClientAuthorizationProcessor clientAuthorizationProcessor;

    private ClientAuthorizationWriter clientAuthorizationWriter;

    @Autowired
    public ClientAuthorizationStep(
        BatchStepBuilder<ClientAuthorizationOld, ClientAuthorizationOld> stepBuilder,
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

    public boolean isEntityActive() {
        return entityActive;
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
