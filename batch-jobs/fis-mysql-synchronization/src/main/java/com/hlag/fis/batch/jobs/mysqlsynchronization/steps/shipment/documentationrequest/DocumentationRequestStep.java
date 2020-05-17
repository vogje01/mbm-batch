package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationrequest;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.DocumentationRequestOld;
import com.hlag.fis.db.db2.repository.DocumentationRequestOldRepository;
import com.hlag.fis.db.mysql.model.DocumentationRequest;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DocumentationRequestStep {

    private static final String STEP_NAME = "Synchronize Documentation Requests";

    @Value("${documentationRequest.chunkSize}")
    private int chunkSize;

    @Value("${documentationRequest.cutOffHours}")
    private int cutOffHours;

    @Value("${documentationRequest.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<DocumentationRequestOld, DocumentationRequest> stepBuilder;

    private DocumentationRequestOldRepository documentationRequestOldRepository;

    private DocumentationRequestReader documentationRequestReader;

    private DocumentationRequestProcessor documentationRequestProcessor;

    private DocumentationRequestWriter documentationRequestWriter;

    @Autowired
    public DocumentationRequestStep(
            BatchStepBuilder<DocumentationRequestOld, DocumentationRequest> stepBuilder,
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

    @SuppressWarnings("unchecked")
    public Step synchronizeDocumentationRequest() {
        long totalCount = fullSync ? documentationRequestOldRepository.count() : documentationRequestOldRepository.countByLastChange(DateTimeUtils.getCutOffHours(cutOffHours));
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
