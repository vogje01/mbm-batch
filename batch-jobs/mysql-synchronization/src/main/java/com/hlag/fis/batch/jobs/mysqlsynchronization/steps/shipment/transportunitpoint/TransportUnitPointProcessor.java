package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.transportunitpoint;

import com.hlag.fis.db.db2.model.TransportUnitPointOld;
import com.hlag.fis.db.db2.repository.TransportUnitPointOldRepository;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import com.hlag.fis.db.mysql.model.TransportUnitPoint;
import com.hlag.fis.db.mysql.repository.PlannedShipmentRepository;
import com.hlag.fis.db.mysql.repository.TransportUnitPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Item processor for the transport unit point entity.
 * <p>
 * This will create a new normalized MySQL transport unit point entity. The transport unit points are linked through a
 * OneToMany/ManyToOne mapping to the corresponding planned shipment. Owning entity is planned shipment. Relationship
 * between planned shipment and transport unit point is via the planned_shipment_id inside the transport unit point.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
public class TransportUnitPointProcessor implements ItemProcessor<TransportUnitPointOld, TransportUnitPoint> {

    private static final Logger logger = LoggerFactory.getLogger(TransportUnitPointProcessor.class);

    @Value("${transportUnitPoint.fullSync}")
    private boolean fullSync;

    private TransportUnitPointRepository transportUnitPointRepository;

    private TransportUnitPointOldRepository transportUnitPointOldRepository;

    private PlannedShipmentRepository plannedShipmentRepository;

    @Autowired
    public TransportUnitPointProcessor(TransportUnitPointOldRepository transportUnitPointOldRepository,
                                       TransportUnitPointRepository transportUnitPointRepository,
                                       PlannedShipmentRepository plannedShipmentRepository) {
        this.transportUnitPointOldRepository = transportUnitPointOldRepository;
        this.transportUnitPointRepository = transportUnitPointRepository;
        this.plannedShipmentRepository = plannedShipmentRepository;
    }

    /**
     * Item processor for the transport unit point entity.
     * <p>
     * This will create a new normalized MySQL transport unit point entity. The transport unit points are linked through a
     * OneToMany/ManyToOne mapping to the corresponding planned shipment. Owning entity is planned shipment. Relationship
     * between planned shipment and transport unit point is via the planned_shipment_id inside the transport unit point.
     * </p>
     *
     * @param transportUnitPointOld old DB2 transport unit point ID.
     * @return MySQL transport unit point entity.
     */
    @Override
    public TransportUnitPoint process(final TransportUnitPointOld transportUnitPointOld) {
        logger.debug("Processing old transport unit point - id: " + transportUnitPointOld.getId());

        // Get MySQL entity
        Optional<TransportUnitPoint> oldTransportUnitPointOpt = transportUnitPointRepository
                .findByClientAndShipmentNumberAndRelativeNumber(transportUnitPointOld.getId().getClient(),
                        transportUnitPointOld.getId().getNumber(), transportUnitPointOld.getId().getRelativeNumber());

        // Create new entity or update existing one
        TransportUnitPoint newTransportUnitPoint;
        if (oldTransportUnitPointOpt.isPresent()) {
            logger.debug("Transport unit point found - id: " + transportUnitPointOld.getId());
            newTransportUnitPoint = oldTransportUnitPointOpt.get();
            newTransportUnitPoint.update(transportUnitPointOld);
            if (!fullSync && newTransportUnitPoint.getLastChange().equals(transportUnitPointOld.getLastChange())) {
                logger.debug("Transport unit point skipped - id: " + transportUnitPointOld.toString());
                return null;
            }
        } else {
            logger.debug("Transport unit point not found - id: " + transportUnitPointOld.getId());
            newTransportUnitPoint = new TransportUnitPoint();
            newTransportUnitPoint.update(transportUnitPointOld);
        }

        // Get the planned shipment and add it to the transport unit point.
        Optional<PlannedShipment> plannedShipmentOpt = plannedShipmentRepository.findByClientAndNumber(newTransportUnitPoint.getClient(), newTransportUnitPoint.getNumber());
        if (plannedShipmentOpt.isPresent()) {
            logger.debug("Planned shipment found - id: " + plannedShipmentOpt.get().getId());
            newTransportUnitPoint.setPlannedShipment(plannedShipmentOpt.get());
        }
        return newTransportUnitPoint;
    }
}