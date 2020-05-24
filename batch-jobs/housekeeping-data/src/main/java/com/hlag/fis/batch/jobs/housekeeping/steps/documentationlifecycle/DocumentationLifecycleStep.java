package com.hlag.fis.batch.jobs.housekeeping.steps.documentationlifecycle;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.mysql.model.DocumentationLifecycle;
import com.hlag.fis.db.mysql.repository.DocumentationLifecycleRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DocumentationLifecycleStep {

    private static final String STEP_NAME = "Documentation Lifecycles";

    @Value("${shipment.documentation.documentationLifecycle.houseKeepingDays}")
    private int houseKeepingDays;

    @Value("${shipment.documentation.documentationLifecycle.chunkSize}")
    private int chunkSize;

    private DocumentationLifecycleRepository documentationLLifecycleRepository;

    private BatchStepBuilder<DocumentationLifecycle, DocumentationLifecycle> stepBuilder;

    private DocumentationLifecycleReader documentationLifecycleReader;

    private DocumentationLifecycleProcessor documentationLifecycleProcessor;

    private DocumentationLifecycleWriter documentationLifecycleWriter;

    @Autowired
    public DocumentationLifecycleStep(
            BatchStepBuilder<DocumentationLifecycle, DocumentationLifecycle> stepBuilder,
            DocumentationLifecycleRepository documentationLLifecycleRepository,
            DocumentationLifecycleReader documentationLifecycleReader,
            DocumentationLifecycleProcessor documentationLifecycleProcessor,
            DocumentationLifecycleWriter documentationLifecycleWriter) {
        this.stepBuilder = stepBuilder;
        this.documentationLLifecycleRepository = documentationLLifecycleRepository;
        this.documentationLifecycleReader = documentationLifecycleReader;
        this.documentationLifecycleProcessor = documentationLifecycleProcessor;
        this.documentationLifecycleWriter = documentationLifecycleWriter;
    }

    @SuppressWarnings("unchecked")
    public Step houseKeepingDocumentationLifecycles() {
        long totalCount = documentationLLifecycleRepository.countByLastChange(DateTimeUtils.getCutOff(houseKeepingDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(documentationLifecycleReader.getReader())
                .processor(documentationLifecycleProcessor)
                .writer(documentationLifecycleWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
