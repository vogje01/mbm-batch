package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization.organizationplace;

import com.hlag.fis.db.db2.model.OrganizationPlaceOld;
import com.hlag.fis.db.mysql.model.OrganizationPlace;
import com.hlag.fis.db.mysql.repository.OrganizationPlaceRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrganizationPlaceProcessor implements ItemProcessor<OrganizationPlaceOld, OrganizationPlace> {

    @Value("${organizationPlace.fullSync}")
    private boolean fullSync;

    private OrganizationPlaceRepository organizationplaceRepository;

    @Autowired
    public OrganizationPlaceProcessor(OrganizationPlaceRepository organizationplaceRepository) {
        this.organizationplaceRepository = organizationplaceRepository;
    }

    /**
     * Item processor for the organization place place model.
     * <p>
     * This will create a new MySQL organization place place model.
     *
     * @param organizationPlaceOld old DB2 organization place place.
     * @return full developed MySQL organization place place model.
     */
    @Override
    public OrganizationPlace process(OrganizationPlaceOld organizationPlaceOld) {
        OrganizationPlace newOrganizationPlace;
        Optional<OrganizationPlace> oldOrganizationPlaceOptional = organizationplaceRepository.findByIdNumberAndClient(organizationPlaceOld.getId().getIdNumber(), organizationPlaceOld.getId().getClient());
        if (oldOrganizationPlaceOptional.isPresent()) {
            if (!fullSync && oldOrganizationPlaceOptional.get().getLastChange().equals(organizationPlaceOld.getLastChange())) {
                return null;
            }
            newOrganizationPlace = oldOrganizationPlaceOptional.get();
        } else {
            newOrganizationPlace = new OrganizationPlace();
        }
        newOrganizationPlace.update(organizationPlaceOld);
        return newOrganizationPlace;
    }
}