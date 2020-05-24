package com.hlag.fis.batch.jobs.housekeeping.steps.docinstruction;

import com.hlag.fis.db.mysql.model.DocumentationInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DocumentationInstructionProcessor implements ItemProcessor<DocumentationInstruction, DocumentationInstruction> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationInstructionProcessor.class);

    @Override
    public DocumentationInstruction process(DocumentationInstruction documentationInstruction) {
        logger.trace("Delete documentation instruction - id: " + documentationInstruction.getId());
        documentationInstruction.removeAllDocumentationLifeCycles();
        documentationInstruction.setDocumentationRequest(null);
        documentationInstruction.setPlannedShipment(null);
        return documentationInstruction;
    }
}