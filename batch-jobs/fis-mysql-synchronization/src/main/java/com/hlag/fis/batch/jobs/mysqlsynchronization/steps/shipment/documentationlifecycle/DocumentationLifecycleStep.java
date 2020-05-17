package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationlifecycle;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.DocumentationLifecycleOld;
import com.hlag.fis.db.db2.repository.DocumentationLifecycleOldRepository;
import com.hlag.fis.db.mysql.model.DocumentationLifecycle;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DocumentationLifecycleStep {

    private static final String STEP_NAME = "Synchronize Documentation Lifecycles";

    @Value("${documentationLifecycle.chunkSize}")
    private int chunkSize;

    @Value("${documentationLifecycle.cutOffHours}")
    private int cutOffHours;

    @Value("${documentationLifecycle.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<DocumentationLifecycleOld, DocumentationLifecycle> stepBuilder;

    private DocumentationLifecycleOldRepository documentationLifecycleOldRepository;

    private DocumentationLifecycleReader documentationLifecycleReader;

    private DocumentationLifecycleProcessor documentationLifecycleProcessor;

    private DocumentationLifecycleWriter documentationLifecycleWriter;

    @Autowired
    public DocumentationLifecycleStep(
            BatchStepBuilder<DocumentationLifecycleOld, DocumentationLifecycle> stepBuilder,
            DocumentationLifecycleOldRepository documentationLifecycleOldRepository,
            DocumentationLifecycleReader documentationLifecycleReader,
            DocumentationLifecycleProcessor documentationLifecycleProcessor,
            DocumentationLifecycleWriter documentationLifecycleWriter) {
        this.stepBuilder = stepBuilder;
        this.documentationLifecycleOldRepository = documentationLifecycleOldRepository;
        this.documentationLifecycleReader = documentationLifecycleReader;
        this.documentationLifecycleProcessor = documentationLifecycleProcessor;
        this.documentationLifecycleWriter = documentationLifecycleWriter;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeDocumentationLifecycle() {
        long totalCount = fullSync ? documentationLifecycleOldRepository.count() : documentationLifecycleOldRepository.countByLastChange(DateTimeUtils.getCutOffHours(cutOffHours));
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
