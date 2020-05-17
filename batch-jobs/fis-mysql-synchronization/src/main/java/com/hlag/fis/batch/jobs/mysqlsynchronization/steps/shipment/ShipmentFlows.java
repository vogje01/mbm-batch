package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment;

import com.hlag.fis.batch.builder.BatchFlowBuilder;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationinstruction.DocumentationInstructionStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationlifecycle.DocumentationLifecycleStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationrequest.DocumentationRequestStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.plannedshipment.PlannedShipmentStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.transportunitpoint.TransportUnitPointStep;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class ShipmentFlows {

    /**
     * Shipment is active
     */
    @Value("${shipment.entityActive}")
    private boolean shipmentEntityActive;
    /**
     * Planned shipment is active
     */
    @Value("${plannedShipment.entityActive}")
    private boolean plannedShipmentEntityActive;
    /**
     * Transport unit point is active
     */
    @Value("${transportUnitPoint.entityActive}")
    private boolean transportUnitPointEntityActive;
    /**
     * Document requests is active
     */
    @Value("${documentationRequest.entityActive}")
    private boolean documentationRequestEntityActive;
    /**
     * Document instructions is active
     */
    @Value("${documentationInstruction.entityActive}")
    private boolean documentationInstructionEntityActive;
    /**
     * Document lifecycle is active
     */
    @Value("${documentationLifecycle.entityActive}")
    private boolean documentationLifecycleEntityActive;

    private PlannedShipmentStep plannedShipmentStep;
    private TransportUnitPointStep transportUnitPointStep;
    private DocumentationRequestStep documentationRequestStep;
    private DocumentationInstructionStep documentationInstructionStep;
    private DocumentationLifecycleStep documentationLifecycleStep;

    @Autowired
    public ShipmentFlows(
            PlannedShipmentStep plannedShipmentStep,
            TransportUnitPointStep transportUnitPointStep,
            DocumentationRequestStep documentationRequestStep,
            DocumentationInstructionStep documentationInstructionStep,
            DocumentationLifecycleStep documentationLifecycleStep) {
        this.plannedShipmentStep = plannedShipmentStep;
        this.transportUnitPointStep = transportUnitPointStep;
        this.documentationRequestStep = documentationRequestStep;
        this.documentationInstructionStep = documentationInstructionStep;
        this.documentationLifecycleStep = documentationLifecycleStep;
    }

    /**
     * Return a list of shipment booking model flows.
     * <p>
     * The flows will be executed in parallel.
     *
     * @return list of shipment booking steps to be executed in parallel.
     */
    private Flow getBookingEntityFlows() {
        List<Step> steps = new ArrayList<>();
        if (shipmentEntityActive || transportUnitPointEntityActive) {
            steps.add(transportUnitPointStep.synchronizeTransportUnitPoint());
        }
        if (shipmentEntityActive || plannedShipmentEntityActive) {
            steps.add(plannedShipmentStep.synchronizePlannedShipment());
        }
        if (steps.size() > 0) {
            return new BatchFlowBuilder<Flow>("Booking Flows").splitSteps(steps).build();
        }
        return null;
    }

    /**
     * Return a list of shipment documentation model flows.
     * <p>
     * The flows will be executed in parallel.
     *
     * @return list of shipment documentation steps to be executed in parallel.
     */
    private Flow getDocumentationEntityFlows() {
        BatchFlowBuilder<Step> batchFlowBuilder = new BatchFlowBuilder<>("Documentation flows");
        if (shipmentEntityActive || documentationRequestEntityActive) {
            batchFlowBuilder.step(documentationRequestStep.synchronizeDocumentationRequest());
        }
        if (shipmentEntityActive || documentationInstructionEntityActive) {
            batchFlowBuilder.step(documentationInstructionStep.synchronizeDocumentationInstruction());
        }
        if (shipmentEntityActive || documentationLifecycleEntityActive) {
            batchFlowBuilder.step(documentationLifecycleStep.synchronizeDocumentationLifecycle());
        }
        if (batchFlowBuilder.getSize() > 0) {
            return batchFlowBuilder.build();
        }
        return null;
    }

    public Flow getFlows() {
        BatchFlowBuilder<Flow> flowBuilder = new BatchFlowBuilder<>("Shipment flows");
        if (getBookingEntityFlows() != null) {
            flowBuilder = flowBuilder.startFlow(getDocumentationEntityFlows());
            if (getDocumentationEntityFlows() != null) {
                flowBuilder.nextFlow(getDocumentationEntityFlows());
                return flowBuilder.build();
            }
        } else if (getDocumentationEntityFlows() != null) {
            flowBuilder.startFlow(getDocumentationEntityFlows());
            return flowBuilder.build();
        }
        return null;
    }
}
