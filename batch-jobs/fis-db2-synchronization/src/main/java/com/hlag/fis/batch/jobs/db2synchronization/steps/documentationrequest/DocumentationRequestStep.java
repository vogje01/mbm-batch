package com.hlag.fis.batch.jobs.db2synchronization.steps.documentationrequest;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.DocumentationRequestOld;
import com.hlag.fis.db.db2.repository.DocumentationRequestOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationRequestStep {

    private static final String STEP_NAME = "Synchronize Documentation Requests";

    @Value("${dbSync.basis.documentationRequest.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.documentationRequest.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.documentationRequest.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.documentationRequest.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<DocumentationRequestOld, DocumentationRequestOld> stepBuilder;

    private DocumentationRequestOldRepository documentationRequestOldRepository;

    private DocumentationRequestReader documentationRequestReader;

    private DocumentationRequestProcessor documentationRequestProcessor;

    private DocumentationRequestWriter documentationRequestWriter;

    @Autowired
    public DocumentationRequestStep(
        BatchStepBuilder<DocumentationRequestOld, DocumentationRequestOld> stepBuilder,
        DocumentationRequestOldRepository documentationRequestOldRepository,
        DocumentationRequestReader documentationRequestReader,
        DocumentationRequestProcessor documentationRequestProcessor,
        DocumentationRequestWriter documentationRequestWriter) {
        this.stepBuilder = stepBuilder;
        this.documentationRequestOldRepository = documentationRequestOldRepository;
        this.documentationRequestReader = documentationRequestReader;
        this.documentationRequestProcessor = documentationRequestProcessor;
        this.documentationRequestWriter = documentationRequestWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeDocumentationRequests() {
        long totalCount = fullSync ? documentationRequestOldRepository.count() : documentationRequestOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
            .name(STEP_NAME)
            .chunkSize(chunkSize)
            .reader(documentationRequestReader.getReader())
            .processor(documentationRequestProcessor)
            .writer(documentationRequestWriter.getWriter())
            .total(totalCount)
            .build();
    }
}

