package com.hlag.fis.batch.jobs.housekeeping.steps.plannedshipment;

import com.hlag.fis.db.mysql.model.DocumentationInstruction;
import com.hlag.fis.db.mysql.model.DocumentationLifecycle;
import com.hlag.fis.db.mysql.model.DocumentationRequest;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import com.hlag.fis.db.mysql.repository.DocumentationInstructionRepository;
import com.hlag.fis.db.mysql.repository.DocumentationLifecycleRepository;
import com.hlag.fis.db.mysql.repository.DocumentationRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Planned shipment housekeeping processor.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Component
public class PlannedShipmentProcessor implements ItemProcessor<PlannedShipment, PlannedShipment> {

    private static final Logger logger = LoggerFactory.getLogger(PlannedShipmentProcessor.class);

    private DocumentationInstructionRepository documentationInstructionRepository;

    private DocumentationLifecycleRepository documentationLifecycleRepository;

    private DocumentationRequestRepository documentationRequestRepository;

    @Autowired
    public PlannedShipmentProcessor(DocumentationInstructionRepository documentationInstructionRepository,
                                    DocumentationLifecycleRepository documentationLifecycleRepository,
                                    DocumentationRequestRepository documentationRequestRepository) {
        this.documentationInstructionRepository = documentationInstructionRepository;
        this.documentationLifecycleRepository = documentationLifecycleRepository;
        this.documentationRequestRepository = documentationRequestRepository;
    }

    @Override
    public PlannedShipment process(PlannedShipment plannedShipment) {
        List<DocumentationInstruction> documentationInstructions = documentationInstructionRepository.findByPlannedShipment(plannedShipment);
        List<DocumentationLifecycle> documentationLifecycles = documentationLifecycleRepository.findByPlannedShipment(plannedShipment);
        List<DocumentationRequest> documentationRequests = documentationRequestRepository.findByPlannedShipment(plannedShipment);
        if (documentationLifecycles.isEmpty() && documentationInstructions.isEmpty() && documentationRequests.isEmpty()) {
            logger.trace("Planned shipment deleted - id: " + plannedShipment.getId());
            return plannedShipment;
        }
        return null;
    }
}