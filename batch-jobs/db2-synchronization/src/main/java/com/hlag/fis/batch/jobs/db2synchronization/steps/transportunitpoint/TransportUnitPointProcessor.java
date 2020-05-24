package com.hlag.fis.batch.jobs.db2synchronization.steps.transportunitpoint;

import com.hlag.fis.db.db2.model.TransportUnitPointOld;
import com.hlag.fis.db.db2.repository.TransportUnitPointOldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransportUnitPointProcessor implements ItemProcessor<TransportUnitPointOld, TransportUnitPointOld> {

    private static final Logger logger = LoggerFactory.getLogger(TransportUnitPointProcessor.class);

    @Value("${dbSync.shipment.booking.transportUnitPoint.fullSync}")
    private boolean fullSync;

    private TransportUnitPointOldRepository transportUnitPointRepository;

    @Autowired
    public TransportUnitPointProcessor(TransportUnitPointOldRepository transportUnitPointRepository) {
        this.transportUnitPointRepository = transportUnitPointRepository;
    }

    /**
     * Item processor for the transport unit point entity.
     * <p>
     * This will create a new normalized MySQL transport unit point entity. The transport unit points are linked through a
     * OneToMany/ManyToOne mapping. Owning entity is planned shipment. Relationship between planned shipment and transport
     * unit point is via the planned_shipment_id inside the transport unit point.
     * </p>
     *
     * @param transportUnitPointOld old DB2 transport unit point ID.
     * @return MySQL transport unit point entity.
     */
    @Override
    public TransportUnitPointOld process(final TransportUnitPointOld transportUnitPointOld) {
        logger.debug("Processing old transport unit point - id: " + transportUnitPointOld.getId());

        // Get MySQL entity
        Optional<TransportUnitPointOld> oldTransportUnitPointOpt = transportUnitPointRepository.findById(transportUnitPointOld.getId());

        // Create new entity or update existing one
        if (oldTransportUnitPointOpt.isPresent()) {
            if (!fullSync && oldTransportUnitPointOpt.get().getLastChange().equals(transportUnitPointOld.getLastChange())) {
                logger.debug("Transport unit point processed - id: " + transportUnitPointOld.toString());
                return null;
            }
        }
        return anonymize(transportUnitPointOld);
    }

    private TransportUnitPointOld anonymize(TransportUnitPointOld transportUnitPointOld) {
        transportUnitPointOld.getId().setClient("M");
        return transportUnitPointOld;
    }
}