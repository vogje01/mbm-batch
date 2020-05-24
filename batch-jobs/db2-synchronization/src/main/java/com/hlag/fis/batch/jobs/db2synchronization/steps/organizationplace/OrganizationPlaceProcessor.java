package com.hlag.fis.batch.jobs.db2synchronization.steps.organizationplace;

import com.hlag.fis.db.db2.model.OrganizationPlaceOld;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrganizationPlaceProcessor implements ItemProcessor<OrganizationPlaceOld, OrganizationPlaceOld> {

    @Value("${dbSync.organization.organizationPlace.fullSync}")
    private boolean fullSync;

    /**
     * Item processor for the organization place place model.
     * <p>
     * This will create a new MySQL organization place place model.
     *
     * @param organizationPlaceOld old DB2 organization place place.
     * @return full developed MySQL organization place place model.
     */
    @Override
    public OrganizationPlaceOld process(OrganizationPlaceOld organizationPlaceOld) {
        return organizationPlaceOld;
    }
}