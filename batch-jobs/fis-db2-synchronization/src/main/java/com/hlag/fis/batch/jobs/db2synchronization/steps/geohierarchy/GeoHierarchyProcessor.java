package com.hlag.fis.batch.jobs.db2synchronization.steps.geohierarchy;

import com.hlag.fis.db.db2.model.GeoHierarchyOld;
import com.hlag.fis.db.db2.repository.GeoHierarchyOldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GeoHierarchyProcessor implements ItemProcessor<GeoHierarchyOld, GeoHierarchyOld> {

    private static final Logger logger = LoggerFactory.getLogger(GeoHierarchyProcessor.class);

    @Value("${dbSync.organization.geoHierarchy.fullSync}")
    private boolean fullSync;

    private GeoHierarchyOldRepository geoHierarchyOldRepository;

    @Autowired
    public GeoHierarchyProcessor(GeoHierarchyOldRepository geoHierarchyOldRepository) {
        this.geoHierarchyOldRepository = geoHierarchyOldRepository;
    }

    /**
     * Item processor for the geoHierarchy entities.
     * <p>
     * This will create a new MySQL geoHierarchy model.
     *
     * @param geoHierarchyOld old DB2 geoHierarchy.
     * @return full developed MySQL geoHierarchy model.
     */
    @Override
    public GeoHierarchyOld process(GeoHierarchyOld geoHierarchyOld) {
        Optional<GeoHierarchyOld> oldGeoHierarchy = geoHierarchyOldRepository.findById(geoHierarchyOld.getId());
        if (oldGeoHierarchy.isPresent()) {
            if (fullSync || !oldGeoHierarchy.get().getLastChange().equals(geoHierarchyOld.getLastChange())) {
                return geoHierarchyOld;
            }
        }
        return null;
    }
}