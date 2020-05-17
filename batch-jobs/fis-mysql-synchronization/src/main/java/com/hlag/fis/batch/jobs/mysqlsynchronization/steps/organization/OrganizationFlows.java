package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization;

import com.hlag.fis.batch.builder.BatchFlowBuilder;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization.geohierarchy.GeoHierarchyStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization.location.LocationStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization.organizationplace.OrganizationPlaceStep;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class OrganizationFlows {
    /**
     * Organization is active
     */
    @Value("${organization.entityActive}")
    private boolean organizationEntityActive;
    /**
     * Geo hierarchy are active
     */
    @Value("${geoHierarchy.entityActive}")
    private boolean geoHierarchyEntityActive;
    /**
     * Organization places are active
     */
    @Value("${organizationPlace.entityActive}")
    private boolean organizationPlaceEntityActive;
    /**
     * Locations are active
     */
    @Value("${location.entityActive}")
    private boolean locationEntityActive;

    private OrganizationPlaceStep organizationPlaceStep;
    private LocationStep locationStep;
    private GeoHierarchyStep geoHierarchyStep;

    @Autowired
    public OrganizationFlows(
            OrganizationPlaceStep organizationPlaceStep,
            LocationStep locationStep,
            GeoHierarchyStep geoHierarchyStep) {
        this.organizationPlaceStep = organizationPlaceStep;
        this.locationStep = locationStep;
        this.geoHierarchyStep = geoHierarchyStep;
    }

    /**
     * Return a list of organization place model flows.
     * <p>
     * The flows will be executed in parallel.
     *
     * @return list of organization place steps to be executed in parallel.
     */
    public Flow getFlows() {
        List<Step> steps = new ArrayList<>();
        if (organizationEntityActive || organizationPlaceEntityActive) {
            steps.add(organizationPlaceStep.synchronizeOrganizationPlace());
        }
        if (organizationEntityActive || locationEntityActive) {
            steps.add(locationStep.synchronizeLocation());
        }
        if (organizationEntityActive || geoHierarchyEntityActive) {
            steps.add(geoHierarchyStep.synchronizeGeoHierarchy());
        }
        if (steps.size() > 0) {
            return new BatchFlowBuilder<Flow>("Organization flows").splitSteps(steps).build();
        }
        return null;
    }
}
