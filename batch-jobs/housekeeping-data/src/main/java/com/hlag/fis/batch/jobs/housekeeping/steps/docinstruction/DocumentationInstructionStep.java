package com.hlag.fis.batch.jobs.housekeeping.steps.docinstruction;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.mysql.model.DocumentationInstruction;
import com.hlag.fis.db.mysql.repository.DocumentationInstructionRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Documentation instruction housekeeping step.
 * <p>
 * Defined the reader, processor and the writer implementations. The step has a retry limit of 3 and uses the
 * {@link DocumentationInstructionReader}, {@link DocumentationInstructionProcessor} and
 * {@link DocumentationInstructionWriter}.
 * </p>
 * <p>
 * Related documentation life cycles will be deleted as well.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @since 0.0.3
 */
@Component
public class DocumentationInstructionStep {

    private static final String STEP_NAME = "Documentation Instructions";

    @Value("${shipment.documentation.documentationInstruction.houseKeepingDays}")
    private int houseKeepingDays;

    @Value("${shipment.documentation.documentationInstruction.chunkSize}")
    private int chunkSize;

    private BatchStepBuilder<DocumentationInstruction, DocumentationInstruction> stepBuilder;

    private DocumentationInstructionRepository documentationLInstructionRepository;

    private DocumentationInstructionReader documentationInstructionReader;

    private DocumentationInstructionProcessor documentationInstructionProcessor;

    private DocumentationInstructionWriter documentationInstructionWriter;

    @Autowired
    public DocumentationInstructionStep(
            BatchStepBuilder<DocumentationInstruction, DocumentationInstruction> stepBuilder,
            DocumentationInstructionRepository documentationLInstructionRepository,
            DocumentationInstructionReader documentationInstructionReader,
            DocumentationInstructionProcessor documentationInstructionProcessor,
            DocumentationInstructionWriter documentationInstructionWriter) {
        this.stepBuilder = stepBuilder;
        this.documentationLInstructionRepository = documentationLInstructionRepository;
        this.documentationInstructionReader = documentationInstructionReader;
        this.documentationInstructionProcessor = documentationInstructionProcessor;
        this.documentationInstructionWriter = documentationInstructionWriter;
    }

    @SuppressWarnings("unchecked")
    public Step houseKeepingDocumentationInstructions() {
        long totalCount = documentationLInstructionRepository.countByLastChange(DateTimeUtils.getCutOff(houseKeepingDays));
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
