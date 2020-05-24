package com.hlag.fis.batch.jobs.db2synchronization.steps.clientrole;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.ClientOld;
import com.hlag.fis.db.db2.repository.ClientRoleOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientRoleStep {

    private static final String STEP_NAME = "Synchronize Clients Roles";

    @Value("${dbSync.basis.clientRole.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.clientRole.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.client.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.client.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<ClientOld, ClientOld> stepBuilder;

    private ClientRoleOldRepository clientRoleOldRepository;

    private ClientRoleReader clientRoleReader;

    private ClientRoleProcessor clientRoleProcessor;

    private ClientRoleWriter clientRoleWriter;

    @Autowired
    public ClientRoleStep(
            BatchStepBuilder<ClientOld, ClientOld> stepBuilder,
            ClientRoleOldRepository clientRoleOldRepository,
            ClientRoleReader clientRoleReader,
            ClientRoleProcessor clientRoleProcessor,
            ClientRoleWriter clientRoleWriter) {
        this.stepBuilder = stepBuilder;
        this.clientRoleOldRepository = clientRoleOldRepository;
        this.clientRoleReader = clientRoleReader;
        this.clientRoleProcessor = clientRoleProcessor;
        this.clientRoleWriter = clientRoleWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeClientRole() {
        long totalCount = fullSync ? clientRoleOldRepository.count() : clientRoleOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(clientRoleReader.getReader())
                .processor(clientRoleProcessor)
                .writer(clientRoleWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
