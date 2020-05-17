package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.plannedshipment;

import com.hlag.fis.db.db2.model.PlannedShipmentOld;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import com.hlag.fis.db.mysql.repository.PlannedShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlannedShipmentProcessor implements ItemProcessor<PlannedShipmentOld, PlannedShipment> {

    private static final Logger logger = LoggerFactory.getLogger(PlannedShipmentProcessor.class);

    @Value("${plannedShipment.fullSync}")
    private boolean fullSync;

    private PlannedShipmentRepository plannedShipmentRepository;

    @Autowired
    public PlannedShipmentProcessor(PlannedShipmentRepository plannedShipmentRepository) {
        this.plannedShipmentRepository = plannedShipmentRepository;
    }

    @Override
    public PlannedShipment process(PlannedShipmentOld plannedShipmentOld) {
        PlannedShipment newPlannedShipment;
        Optional<PlannedShipment> oldPlannedShipmentOpt = plannedShipmentRepository.findByClientAndNumber(plannedShipmentOld.getId().getClient(), plannedShipmentOld.getId().getNumber());
        if (oldPlannedShipmentOpt.isPresent()) {
            logger.debug("Planned shipment found shipment - id: " + plannedShipmentOld.getId().toString());
            if (!fullSync && oldPlannedShipmentOpt.get().getLastChange().equals(plannedShipmentOld.getLastChange())) {
                // Nothing to do
                logger.debug("Planned shipment skipped - id: " + plannedShipmentOld.getId().toString());
                return null;
            }
            newPlannedShipment = oldPlannedShipmentOpt.get();
        } else {
            logger.debug("Planned shipment not found shipment - id: " + plannedShipmentOld.getId().toString());
            newPlannedShipment = new PlannedShipment();
        }
        newPlannedShipment.update(plannedShipmentOld);
        return newPlannedShipment;
    }
}