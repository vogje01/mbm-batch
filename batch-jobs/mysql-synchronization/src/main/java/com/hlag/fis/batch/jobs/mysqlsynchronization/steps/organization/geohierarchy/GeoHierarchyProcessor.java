package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization.geohierarchy;

import com.hlag.fis.db.db2.model.GeoHierarchyOld;
import com.hlag.fis.db.mysql.model.GeoHierarchy;
import com.hlag.fis.db.mysql.repository.GeoHierarchyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Geo hierarchy processor.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Component
public class GeoHierarchyProcessor implements ItemProcessor<GeoHierarchyOld, GeoHierarchy> {

    private static final Logger logger = LoggerFactory.getLogger(GeoHierarchyProcessor.class);

    @Value("${geoHierarchy.fullSync}")
    private boolean fullSync;

    private GeoHierarchyRepository geoHierarchyRepository;

    @Autowired
    public GeoHierarchyProcessor(GeoHierarchyRepository geoHierarchyRepository) {
        this.geoHierarchyRepository = geoHierarchyRepository;
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
    public GeoHierarchy process(GeoHierarchyOld geoHierarchyOld) {
        GeoHierarchy newGeoHierarchy;
        Optional<GeoHierarchy> geoHierarchyOptional = geoHierarchyRepository.findGeoHierarchy(geoHierarchyOld.getId().getClient(), geoHierarchyOld.getId().getGeoRegionId(),
                geoHierarchyOld.getId().getGeoSubRegionId(), geoHierarchyOld.getId().getGeoAreaId(), geoHierarchyOld.getId().getGeoSubAreaId(),
                geoHierarchyOld.getId().getGeoDistrictId());
        if (geoHierarchyOptional.isPresent()) {
            if (!fullSync && geoHierarchyOptional.get().getLastChange().equals(geoHierarchyOld.getLastChange())) {
                // Nothing to do
                return null;
            }
            newGeoHierarchy = geoHierarchyOptional.get();
        } else {
            logger.debug("GeoHierarchy not found - uuid: " + geoHierarchyOld.getId().toString());
            newGeoHierarchy = new GeoHierarchy();
        }
        newGeoHierarchy.update(geoHierarchyOld);
        return newGeoHierarchy;
    }
}