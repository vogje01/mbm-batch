package com.hlag.fis.batch.jobs.db2synchronization.steps.securityorganization;

import com.hlag.fis.db.db2.model.SecurityOrganizationOld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityOrganizationProcessor implements ItemProcessor<SecurityOrganizationOld, SecurityOrganizationOld> {

    private static final Logger logger = LoggerFactory.getLogger(SecurityOrganizationProcessor.class);

    @Value("${dbSync.basis.securityOrganization.fullSync}")
    private boolean fullSync;

    /**
     * Item processor for the user role model.
     * <p>
     * This will create a new MySQL user role model.
     *
     * @param securityOrganizationOld old DB2 user .
     * @return full developed MySQL user role model.
     */
    @Override
    public SecurityOrganizationOld process(SecurityOrganizationOld securityOrganizationOld) {
        logger.debug("Processing old user role - " + securityOrganizationOld.toString());
        return securityOrganizationOld;
    }
}