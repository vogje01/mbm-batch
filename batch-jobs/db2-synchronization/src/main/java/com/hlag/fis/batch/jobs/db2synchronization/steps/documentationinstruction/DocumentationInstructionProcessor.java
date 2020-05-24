package com.hlag.fis.batch.jobs.db2synchronization.steps.documentationinstruction;

import com.hlag.fis.db.db2.model.DocumentationInstructionOld;
import com.hlag.fis.db.db2.repository.DocumentationInstructionOldRepository;
import com.hlag.fis.db.mysql.model.DocumentationInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DocumentationInstructionProcessor implements ItemProcessor<DocumentationInstructionOld, DocumentationInstructionOld> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationInstructionProcessor.class);

    @Value("${dbSync.shipment.documentation.documentationInstruction.fullSync}")
    private boolean fullSync;

    private DocumentationInstructionOldRepository documentationInstructionOldRepository;

    @Autowired
    public DocumentationInstructionProcessor(
            DocumentationInstructionOldRepository documentationInstructionOldRepository) {
        this.documentationInstructionOldRepository = documentationInstructionOldRepository;
    }

    /**
     * Item processor for the planned shipment model.
     * <p>
     * This will create a new MySQL planned shipment model.
     *
     * @param documentationInstructionOld old DB2 planned shipment.
     * @return full developed MySQL planned shipment model.
     */
    @Override
    public DocumentationInstructionOld process(DocumentationInstructionOld documentationInstructionOld) {
        logger.debug("Processing old documentation instruction - " + documentationInstructionOld.toString());
        DocumentationInstruction newDocInstruction;
        Optional<DocumentationInstructionOld> oldDocInstructionOpt = documentationInstructionOldRepository.findById(documentationInstructionOld.getId());
        if (oldDocInstructionOpt.isPresent()) {
            if (fullSync || oldDocInstructionOpt.get().getLastChange().equals(documentationInstructionOld.getLastChange())) {
                return oldDocInstructionOpt.get();
            }
        }
        return null;
    }
}