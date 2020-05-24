package com.hlag.fis.batch.jobs.db2synchronization.steps.documentationinstruction;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.DocumentationInstructionOld;
import com.hlag.fis.db.db2.repository.DocumentationInstructionOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationInstructionStep {

    private static final String STEP_NAME = "Synchronize Documentation Instruction";

    @Value("${dbSync.basis.documentationInstruction.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.documentationInstruction.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.documentationInstruction.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.documentationInstruction.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<DocumentationInstructionOld, DocumentationInstructionOld> stepBuilder;

    private DocumentationInstructionOldRepository documentationInstructionOldRepository;

    private DocumentationInstructionReader documentationInstructionReader;

    private DocumentationInstructionProcessor documentationInstructionProcessor;

    private DocumentationInstructionWriter documentationInstructionWriter;

    @Autowired
    public DocumentationInstructionStep(
            BatchStepBuilder<DocumentationInstructionOld, DocumentationInstructionOld> stepBuilder,
            DocumentationInstructionOldRepository documentationInstructionOldRepository,
            DocumentationInstructionReader documentationInstructionReader,
            DocumentationInstructionProcessor documentationInstructionProcessor,
            DocumentationInstructionWriter documentationInstructionWriter) {
        this.stepBuilder = stepBuilder;
        this.documentationInstructionOldRepository = documentationInstructionOldRepository;
        this.documentationInstructionReader = documentationInstructionReader;
        this.documentationInstructionProcessor = documentationInstructionProcessor;
        this.documentationInstructionWriter = documentationInstructionWriter;
    }

    public boolean isEntityActive() {
        return entityActive;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeDocumentationInstructions() {
        long totalCount = fullSync ? documentationInstructionOldRepository.count() : documentationInstructionOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(documentationInstructionReader.getReader())
                .processor(documentationInstructionProcessor)
                .writer(documentationInstructionWriter.getWriter())
                .total(totalCount)
                .build();
    }
}

