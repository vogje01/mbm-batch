package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.client;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.ClientOld;
import com.hlag.fis.db.db2.repository.ClientOldRepository;
import com.hlag.fis.db.mysql.model.Client;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientStep {

    private static final String STEP_NAME = "Synchronize Clients";

    @Value("${client.chunkSize}")
    private int chunkSize;

    @Value("${client.cutOffDays}")
    private int cutOffDays;

    @Value("${client.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<ClientOld, Client> stepBuilder;

    private ClientOldRepository clientOldRepository;

    private ClientReader clientReader;

    private ClientProcessor clientProcessor;

    private ClientWriter clientWriter;

    @Autowired
    public ClientStep(
            BatchStepBuilder<ClientOld, Client> stepBuilder,
            ClientOldRepository clientOldRepository,
            ClientReader clientReader,
            ClientProcessor clientProcessor,
            ClientWriter clientWriter) {
        this.stepBuilder = stepBuilder;
        this.clientOldRepository = clientOldRepository;
        this.clientReader = clientReader;
        this.clientProcessor = clientProcessor;
        this.clientWriter = clientWriter;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeClient() {
        long totalCount = fullSync ? clientOldRepository.count() : clientOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(clientReader.getReader())
                .processor(clientProcessor)
                .writer(clientWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
