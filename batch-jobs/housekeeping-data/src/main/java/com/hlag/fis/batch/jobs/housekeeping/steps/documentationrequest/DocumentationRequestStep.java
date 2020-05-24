package com.hlag.fis.batch.jobs.housekeeping.steps.documentationrequest;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.mysql.model.DocumentationRequest;
import com.hlag.fis.db.mysql.repository.DocumentationRequestRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DocumentationRequestStep {

    private static final String STEP_NAME = "Documentation Requests";

    @Value("${shipment.documentation.documentationRequest.houseKeepingDays}")
    private int houseKeepingDays;

    @Value("${shipment.documentation.documentationRequest.chunkSize}")
    private int chunkSize;

    private DocumentationRequestRepository documentationLRequestRepository;

    private BatchStepBuilder<DocumentationRequest, DocumentationRequest> stepBuilder;

    private DocumentationRequestReader documentationRequestReader;

    private DocumentationRequestProcessor documentationRequestProcessor;

    private DocumentationRequestWriter documentationRequestWriter;

    @Autowired
    public DocumentationRequestStep(
            BatchStepBuilder<DocumentationRequest, DocumentationRequest> stepBuilder,
            DocumentationRequestRepository documentationLRequestRepository,
            DocumentationRequestReader documentationRequestReader,
            DocumentationRequestProcessor documentationRequestProcessor,
            DocumentationRequestWriter documentationRequestWriter) {
        this.stepBuilder = stepBuilder;
        this.documentationLRequestRepository = documentationLRequestRepository;
        this.documentationRequestReader = documentationRequestReader;
        this.documentationRequestProcessor = documentationRequestProcessor;
        this.documentationRequestWriter = documentationRequestWriter;
    }

    @SuppressWarnings("unchecked")
    public Step houseKeepingDocumentationRequests() {
        long totalCount = documentationLRequestRepository.countByLastChange(DateTimeUtils.getCutOff(houseKeepingDays));
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
