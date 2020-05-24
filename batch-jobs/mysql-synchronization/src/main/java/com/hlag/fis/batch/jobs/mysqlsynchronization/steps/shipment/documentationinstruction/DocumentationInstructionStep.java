package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationinstruction;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.DocumentationInstructionOld;
import com.hlag.fis.db.db2.repository.DocumentationInstructionOldRepository;
import com.hlag.fis.db.mysql.model.DocumentationInstruction;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DocumentationInstructionStep {

    private static final String STEP_NAME = "Synchronize Documentation Instructions";

    @Value("${documentationInstruction.chunkSize}")
    private int chunkSize;

    @Value("${documentationInstruction.cutOffHours}")
    private int cutOffHours;

    @Value("${documentationInstruction.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<DocumentationInstructionOld, DocumentationInstruction> stepBuilder;

    private DocumentationInstructionOldRepository documentationInstructionOldRepository;

    private DocumentationInstructionReader documentationInstructionReader;

    private DocumentationInstructionProcessor documentationInstructionProcessor;

    private DocumentationInstructionWriter documentationInstructionWriter;

    @Autowired
    public DocumentationInstructionStep(
            BatchStepBuilder<DocumentationInstructionOld, DocumentationInstruction> stepBuilder,
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

    @SuppressWarnings("unchecked")
    public Step synchronizeDocumentationInstruction() {
        long totalCount = fullSync ? documentationInstructionOldRepository.count() : documentationInstructionOldRepository.countByLastChange(DateTimeUtils.getCutOffHours(cutOffHours));
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
