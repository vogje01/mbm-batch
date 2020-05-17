package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization.location;

import com.hlag.fis.db.db2.model.LocationOld;
import com.hlag.fis.db.mysql.model.GeoHierarchy;
import com.hlag.fis.db.mysql.model.Location;
import com.hlag.fis.db.mysql.repository.GeoHierarchyRepository;
import com.hlag.fis.db.mysql.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Location synchronization processor.
 * <p>
 * Adds also the relationship to the geo location.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
public class LocationProcessor implements ItemProcessor<LocationOld, Location> {

    private static final Logger logger = LoggerFactory.getLogger(LocationProcessor.class);

    @Value("${location.fullSync}")
    private boolean fullSync;

    private GeoHierarchyRepository geoHierarchyRepository;

    private LocationRepository locationRepository;

    @Autowired
    public LocationProcessor(GeoHierarchyRepository geoHierarchyRepository, LocationRepository locationRepository) {
        this.geoHierarchyRepository = geoHierarchyRepository;
        this.locationRepository = locationRepository;
    }

    /**
     * Item processor for the location entities.
     * <p>
     * This will create a new MySQL location model.
     * </p>
     *
     * @param locationOld old DB2 location.
     * @return MySQL location entity.
     */
    @Override
    public Location process(LocationOld locationOld) {
        Location newLocation;
        Optional<Location> oldLocationOptional = locationRepository.findByClientAndNumber(locationOld.getId().getClient(), locationOld.getId().getNumber());
        if (oldLocationOptional.isPresent()) {
            if (!fullSync && oldLocationOptional.get().getLastChange().equals(locationOld.getLastChange())) {
                // Nothing to do
                return null;
            }
            newLocation = oldLocationOptional.get();
        } else {
            newLocation = new Location();
        }
        newLocation.update(locationOld);

        getGeoHierarchyConstraint(locationOld, newLocation);

        return newLocation;
    }

    /**
     * Add the one to one relationship from location to geo hierarchy. The relationship
     * is mapped by the geo_hierarchy_id uuid in the location.
     *
     * @param locationOld old location
     * @param location    location
     */
    private void getGeoHierarchyConstraint(LocationOld locationOld, Location location) {
        Optional<GeoHierarchy> geoHierarchyOptional = geoHierarchyRepository.findGeoHierarchy(locationOld.getId().getClient(), locationOld.getRegionId(), locationOld.getSubRegionId(),
                locationOld.getAreaId(), locationOld.getSubAreaId(), locationOld.getDistrictId());
        geoHierarchyOptional.ifPresent(geoHierarchy -> {
            if (location.getGeoHierarchy() == null || !location.getGeoHierarchy().getId().equals(geoHierarchy.getId())) {
                location.setGeoHierarchy(geoHierarchy);
            }
        });
    }
}