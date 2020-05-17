package com.hlag.fis.batch.jobs.db2synchronization.steps.plannedshipment;

import com.hlag.fis.db.db2.model.PlannedShipmentOld;
import com.hlag.fis.db.db2.repository.PlannedShipmentOldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlannedShipmentProcessor implements ItemProcessor<PlannedShipmentOld, PlannedShipmentOld> {

    private static final Logger logger = LoggerFactory.getLogger(PlannedShipmentProcessor.class);

    @Value("${dbSync.shipment.booking.plannedShipment.fullSync}")
    private boolean fullSync;

    private PlannedShipmentOldRepository plannedShipmentOldRepository;

    @Autowired
    public PlannedShipmentProcessor(PlannedShipmentOldRepository plannedShipmentOldRepository) {
        this.plannedShipmentOldRepository = plannedShipmentOldRepository;
    }

    @Override
    public PlannedShipmentOld process(PlannedShipmentOld plannedShipmentOld) {
        Optional<PlannedShipmentOld> oldPlannedShipmentOpt = plannedShipmentOldRepository.findById(plannedShipmentOld.getId());
        if (oldPlannedShipmentOpt.isPresent()) {
            logger.debug("Planned shipment found shipment - id: " + plannedShipmentOld.getId().toString());
            if (!fullSync && oldPlannedShipmentOpt.get().getLastChange().equals(plannedShipmentOld.getLastChange())) {
                return null;
            }
        }
        return anonymize(plannedShipmentOld);
    }

    private PlannedShipmentOld anonymize(PlannedShipmentOld plannedShipment) {
        plannedShipment.getId().setClient("M");
        return plannedShipment;
    }
}