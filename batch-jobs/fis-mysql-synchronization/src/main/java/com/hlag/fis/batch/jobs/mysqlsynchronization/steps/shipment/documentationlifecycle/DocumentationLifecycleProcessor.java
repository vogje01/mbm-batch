package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationlifecycle;

import com.hlag.fis.db.db2.model.DocumentationLifecycleOld;
import com.hlag.fis.db.mysql.model.*;
import com.hlag.fis.db.mysql.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Documentation life cycle processor.
 * <p>
 * This processor inserts all the constraints to a documentation life cycles. Currently this are:
 * <ul>
 * <li><b>Planned shipment:</b> the planned shipment is retrieved by shipment number and client.</li>
 * <li><b>Documentation request:</b> the documentation request is retrieved by name and supplement.</li>
 * </ul>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Component
public class DocumentationLifecycleProcessor implements ItemProcessor<DocumentationLifecycleOld, DocumentationLifecycle> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationLifecycleProcessor.class);

    @Value("${documentationLifecycle.fullSync}")
    private boolean fullSync;

    private PlannedShipmentRepository plannedShipmentRepository;

    private DocumentationRequestRepository documentationRequestRepository;

    private DocumentationInstructionRepository documentationInstructionRepository;

    private DocumentationLifecycleRepository documentationLifecycleRepository;

    private OrganizationPlaceRepository organizationPlaceRepository;

    @Autowired
    public DocumentationLifecycleProcessor(
            PlannedShipmentRepository plannedShipmentRepository,
            DocumentationRequestRepository documentationRequestRepository,
            DocumentationInstructionRepository documentationInstructionRepository,
            DocumentationLifecycleRepository documentationLifecycleRepository,
            OrganizationPlaceRepository organizationPlaceRepository) {
        this.plannedShipmentRepository = plannedShipmentRepository;
        this.documentationRequestRepository = documentationRequestRepository;
        this.documentationInstructionRepository = documentationInstructionRepository;
        this.documentationLifecycleRepository = documentationLifecycleRepository;
        this.organizationPlaceRepository = organizationPlaceRepository;
    }

    /**
     * Item processor for the documentation life cycle entity.
     * <p>
     * This will create a new normalized MySQL life cycle entity.
     *
     * @param documentationLifecycleOld old DB2 life cycle entity.
     * @return full developed MySQL lifecycle entity.
     */
    @Override
    public DocumentationLifecycle process(DocumentationLifecycleOld documentationLifecycleOld) {
        logger.debug("Processing old documentation life cycle - " + documentationLifecycleOld.toString());
        DocumentationLifecycle newDocLifecycle = null;
        Optional<DocumentationLifecycle> oldDocLifecycleOpt = documentationLifecycleRepository.findDocumentationLifecycle(documentationLifecycleOld.getId().getShipmentClient(),
                documentationLifecycleOld.getId().getShipmentNumber(), documentationLifecycleOld.getId().getDocumentationRequestRelativeNumber(),
                documentationLifecycleOld.getId().getRelativeNumber());
        if (oldDocLifecycleOpt.isPresent()) {
            newDocLifecycle = oldDocLifecycleOpt.get();
            if (!fullSync && newDocLifecycle.getLastChange().equals(documentationLifecycleOld.getLastChange())) {
                // Nothing to do
                return null;
            }
            newDocLifecycle.update(documentationLifecycleOld);
        } else {
            newDocLifecycle = new DocumentationLifecycle();
            newDocLifecycle.update(documentationLifecycleOld);
        }

        getPlannedShipmentConstraint(documentationLifecycleOld, newDocLifecycle);
        getDocumentationRequestConstraint(documentationLifecycleOld, newDocLifecycle);
        getDocumentationInstructionConstraint(documentationLifecycleOld, newDocLifecycle);
        getStatusPartyConstraint(documentationLifecycleOld, newDocLifecycle);
        getChangedByConstraint(documentationLifecycleOld, newDocLifecycle);

        return newDocLifecycle;
    }

    /**
     * Add the one to many relationship from planned shipment to documentation request. The relationship
     * ist mapped by the planned shipment uuid in the documentation request.
     *
     * @param documentationLifecycleOld old documentation lifecycle
     * @param documentationLifecycle    documentation lifecycle
     */
    private void getPlannedShipmentConstraint(DocumentationLifecycleOld documentationLifecycleOld, DocumentationLifecycle documentationLifecycle) {
        Optional<PlannedShipment> plannedShipmentOpt = plannedShipmentRepository.findByClientAndNumber(documentationLifecycleOld.getId().getShipmentClient(), documentationLifecycleOld.getId().getShipmentNumber());
        if (plannedShipmentOpt.isPresent()) {
            PlannedShipment plannedShipment = plannedShipmentOpt.get();
            if (documentationLifecycle.getPlannedShipment() == null || !documentationLifecycle.getPlannedShipment().getId().equals(plannedShipment.getId())) {
                documentationLifecycle.setPlannedShipment(plannedShipment);
            }
        }
    }

    /**
     * Add the one to many relationship from documentation life cycle documentation request. The relationship
     * is mapped by the documentation request uuid in the documentation life cycle.
     *
     * @param documentationLifecycleOld old documentation lifecycle.
     * @param documentationLifecycle    documentation lifecycle.
     */
    private void getDocumentationRequestConstraint(DocumentationLifecycleOld documentationLifecycleOld, DocumentationLifecycle documentationLifecycle) {
        if (documentationLifecycle.getPlannedShipment() == null) {
            return;
        }
        Optional<DocumentationRequest> documentationRequestOpt = documentationRequestRepository.findByPlannedShipmentAndRelativeNumber(
                documentationLifecycle.getPlannedShipment(), documentationLifecycleOld.getId().getDocumentationRequestRelativeNumber());
        if (documentationRequestOpt.isPresent()) {
            DocumentationRequest documentationRequest = documentationRequestOpt.get();
            if (documentationLifecycle.getDocumentationRequest() == null || !documentationLifecycle.getDocumentationRequest().getId().equals(documentationRequest.getId())) {
                documentationLifecycle.setDocumentationRequest(documentationRequest);
            }
        }
    }

    /**
     * Add the one to many relationship from documentation life cycle documentation instruction. The relationship
     * is mapped by the documentation instruction uuid in the documentation life cycle.
     *
     * @param documentationLifecycleOld old documentation lifecycle.
     * @param documentationLifecycle    documentation lifecycle.
     */
    private void getDocumentationInstructionConstraint(DocumentationLifecycleOld documentationLifecycleOld, DocumentationLifecycle documentationLifecycle) {
        if (documentationLifecycle.getPlannedShipment() == null) {
            return;
        }
        Optional<DocumentationInstruction> documentationInstructionOpt = documentationInstructionRepository.findByPlannedShipmentAndRelativeNumber(
                documentationLifecycle.getPlannedShipment(), documentationLifecycleOld.getId().getDocumentationRequestRelativeNumber());
        if (documentationInstructionOpt.isPresent()) {
            DocumentationInstruction documentationInstruction = documentationInstructionOpt.get();
            if (documentationLifecycle.getDocumentationInstruction() == null || !documentationLifecycle.getDocumentationInstruction()
                    .getId()
                    .equals(documentationInstruction.getId())) {
                documentationLifecycle.setDocumentationInstruction(documentationInstruction);
            }
        }
    }

    /**
     * Add the one to many relationship from documentation life cycle to organization place. The relationship
     * is mapped by the status_party_id uuid in the documentation life cycle.
     *
     * @param documentationLifecycleOld old documentation lifecycle.
     * @param documentationLifecycle    documentation lifecycle.
     */
    private void getStatusPartyConstraint(DocumentationLifecycleOld documentationLifecycleOld, DocumentationLifecycle documentationLifecycle) {
        List<OrganizationPlace> statusParties = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(
                documentationLifecycleOld.getId().getShipmentClient(), documentationLifecycleOld.getStatusPartyMatchCodeName(),
                documentationLifecycleOld.getStatusPartyMatchCodeSupplement());
        if (!statusParties.isEmpty()) {
            OrganizationPlace statusParty = statusParties.get(0);
            if (documentationLifecycle.getStatusParty() == null || !documentationLifecycle.getStatusParty().getId().equals(statusParty.getId())) {
                documentationLifecycle.setStatusParty(statusParty);
            }
        }
    }

    /**
     * Add the one to one relationship from documentation life cycle to organization places. The relationship
     * is mapped by the changed_by_id uuid in the documentation life cycle.
     *
     * @param documentationLifecycleOld old documentation lifecycle.
     * @param documentationLifecycle    documentation lifecycle.
     */
    private void getChangedByConstraint(DocumentationLifecycleOld documentationLifecycleOld, DocumentationLifecycle documentationLifecycle) {
        List<OrganizationPlace> changedBys = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(
                documentationLifecycleOld.getId().getShipmentClient(), documentationLifecycleOld.getChangedByMatchCodeName(),
                documentationLifecycleOld.getChangedByMatchCodeSupplement());
        if (!changedBys.isEmpty()) {
            OrganizationPlace changedBy = changedBys.get(0);
            if (documentationLifecycle.getChangedByOrganization() == null || !documentationLifecycle.getChangedByOrganization().getId().equals(changedBy.getId())) {
                documentationLifecycle.setChangedByOrganization(changedBy);
            }
        }
    }
}