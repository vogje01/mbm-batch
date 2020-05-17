package com.hlag.fis.batch.jobs.db2synchronization.steps.documentationlifecycle;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.DocumentationLifecycleOld;
import com.hlag.fis.db.db2.repository.DocumentationLifecycleOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationLifecycleStep {

    private static final String STEP_NAME = "Synchronize Documentation Lifecycles";

    @Value("${dbSync.basis.documentationLifecycle.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.documentationLifecycle.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.documentationLifecycle.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.documentationLifecycle.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<DocumentationLifecycleOld, DocumentationLifecycleOld> stepBuilder;

    private DocumentationLifecycleOldRepository documentationLifecycleOldRepository;

    private DocumentationLifecycleReader documentationLifecycleReader;

    private DocumentationLifecycleProcessor documentationLifecycleProcessor;

    private DocumentationLifecycleWriter documentationLifecycleWriter;

    @Autowired
    public DocumentationLifecycleStep(
        BatchStepBuilder<DocumentationLifecycleOld, DocumentationLifecycleOld> stepBuilder,
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

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeDocumentationLifecycles() {
        long totalCount = fullSync ? documentationLifecycleOldRepository.count() : documentationLifecycleOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
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

