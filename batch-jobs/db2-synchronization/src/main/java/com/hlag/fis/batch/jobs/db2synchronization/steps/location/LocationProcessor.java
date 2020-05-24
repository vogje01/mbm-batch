package com.hlag.fis.batch.jobs.db2synchronization.steps.location;

import com.hlag.fis.db.db2.model.LocationOld;
import com.hlag.fis.db.db2.repository.LocationOldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LocationProcessor implements ItemProcessor<LocationOld, LocationOld> {

    private static final Logger logger = LoggerFactory.getLogger(LocationProcessor.class);

    @Value("${dbSync.organization.location.fullSync}")
    private boolean fullSync;

    private LocationOldRepository locationOldRepository;

    @Autowired
    public LocationProcessor(LocationOldRepository locationOldRepository) {
        this.locationOldRepository = locationOldRepository;
    }

    /**
     * Item processor for the location entities.
     * <p>
     * This will create a new MySQL location model.
     *
     * @param locationOld old DB2 location.
     * @return MySQL location model.
     */
    @Override
    public LocationOld process(LocationOld locationOld) {
        Optional<LocationOld> oldLocation = locationOldRepository.findById(locationOld.getId());
        if (oldLocation.isPresent()) {
            if (fullSync || !oldLocation.get().getLastChange().equals(locationOld.getLastChange())) {
                return locationOld;
            }
        }
        return null;
    }
}