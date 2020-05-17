package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationinstruction;

import com.hlag.fis.db.db2.model.DocumentationInstructionOld;
import com.hlag.fis.db.mysql.model.DocumentationInstruction;
import com.hlag.fis.db.mysql.model.DocumentationRequest;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import com.hlag.fis.db.mysql.repository.DocumentationInstructionRepository;
import com.hlag.fis.db.mysql.repository.DocumentationRequestRepository;
import com.hlag.fis.db.mysql.repository.PlannedShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Documentation instruction synchronization processor.
 * <p>
 * Adds also the relationship to the geo location.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
public class DocumentationInstructionProcessor implements ItemProcessor<DocumentationInstructionOld, DocumentationInstruction> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationInstructionProcessor.class);

    @Value("${documentationInstruction.fullSync}")
    private boolean fullSync;

    private PlannedShipmentRepository plannedShipmentRepository;

    private DocumentationRequestRepository documentationRequestRepository;

    private DocumentationInstructionRepository documentationInstructionRepository;

    @Autowired
    public DocumentationInstructionProcessor(
            PlannedShipmentRepository plannedShipmentRepository,
            DocumentationRequestRepository documentationRequestRepository,
            DocumentationInstructionRepository documentationInstructionRepository) {
        this.plannedShipmentRepository = plannedShipmentRepository;
        this.documentationRequestRepository = documentationRequestRepository;
        this.documentationInstructionRepository = documentationInstructionRepository;
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
    public DocumentationInstruction process(DocumentationInstructionOld documentationInstructionOld) {
        logger.debug("Processing old documentation instruction - " + documentationInstructionOld.toString());
        DocumentationInstruction newDocInstruction;
        Optional<DocumentationInstruction> oldDocInstructionOpt = documentationInstructionRepository.findByClientAndNumberAndRelativeNumber(
                documentationInstructionOld.getId().getClient(), documentationInstructionOld.getId().getNumber(),
                documentationInstructionOld.getId().getRelativeNumber());
        if (oldDocInstructionOpt.isPresent()) {
            newDocInstruction = oldDocInstructionOpt.get();
            if (!fullSync && newDocInstruction.getLastChange().equals(documentationInstructionOld.getLastChange())) {
                // Nothing to do
                return null;
            }
            newDocInstruction.update(documentationInstructionOld);
        } else {
            newDocInstruction = new DocumentationInstruction();
            newDocInstruction.update(documentationInstructionOld);
        }

        getPlannedShipmentConstraint(documentationInstructionOld, newDocInstruction);
        getDocumentationRequestConstraint(documentationInstructionOld, newDocInstruction);

        return newDocInstruction;
    }

    /**
     * Add the one to many relationship from planned shipment to documentation request. The relationship
     * ist mapped by the planned shipment uuid in the documentation request.
     *
     * @param documentationInstruction documentation instruction
     */
    private void getPlannedShipmentConstraint(DocumentationInstructionOld documentationInstructionOld, DocumentationInstruction documentationInstruction) {
        Optional<PlannedShipment> plannedShipmentOpt = plannedShipmentRepository.findByClientAndNumber(
                documentationInstructionOld.getId().getClient(), documentationInstructionOld.getId().getNumber());
        if (plannedShipmentOpt.isPresent()) {
            PlannedShipment plannedShipment = plannedShipmentOpt.get();
            if (documentationInstruction.getPlannedShipment() == null || !documentationInstruction.getPlannedShipment().getId().equals(plannedShipment.getId())) {
                documentationInstruction.setPlannedShipment(plannedShipment);
            }
        }
    }

    /**
     * Add the one to one relationship from documentation instruction to documentation request. The relationship
     * is mapped by the documentation_request_id uuid in the documentation instruction.
     *
     * @param documentationInstruction documentation instruction.
     */
    private void getDocumentationRequestConstraint(DocumentationInstructionOld documentationInstructionOld, DocumentationInstruction documentationInstruction) {
        if (documentationInstruction.getPlannedShipment() == null) {
            return;
        }
        Optional<DocumentationRequest> documentationRequestOpt = documentationRequestRepository.findByPlannedShipmentAndRelativeNumber(
                documentationInstruction.getPlannedShipment(), documentationInstructionOld.getId().getRelativeNumber());
        if (documentationRequestOpt.isPresent()) {
            DocumentationRequest documentationRequest = documentationRequestOpt.get();
            if (documentationInstruction.getDocumentationRequest() == null || !documentationInstruction.getDocumentationRequest()
                    .getId()
                    .equals(documentationRequest.getId())) {
                documentationInstruction.setDocumentationRequest(documentationRequest);
            }
        }
    }
}