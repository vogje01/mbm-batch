package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.securityorganization;

import com.hlag.fis.db.db2.model.SecurityOrganizationOld;
import com.hlag.fis.db.mysql.model.SecurityOrganization;
import com.hlag.fis.db.mysql.repository.SecurityOrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityOrganizationProcessor implements ItemProcessor<SecurityOrganizationOld, SecurityOrganization> {

    private static final Logger logger = LoggerFactory.getLogger(SecurityOrganizationProcessor.class);

    @Value("${securityOrganization.fullSync}")
    private boolean fullSync;

    private SecurityOrganizationRepository securityOrganizationRepository;

    @Autowired
    public SecurityOrganizationProcessor(SecurityOrganizationRepository securityOrganizationRepository) {
        this.securityOrganizationRepository = securityOrganizationRepository;
    }

    /**
     * Item processor for the user role model.
     * <p>
     * This will create a new MySQL user role model.
     *
     * @param securityOrganizationOld old DB2 user .
     * @return full developed MySQL user role model.
     */
    @Override
    public SecurityOrganization process(SecurityOrganizationOld securityOrganizationOld) {
        logger.debug("Processing old security organization - " + securityOrganizationOld.toString());
        SecurityOrganization newSecurityOrganization;
        Optional<SecurityOrganization> oldSecurityOrganization = securityOrganizationRepository.findByClientAndHistoryFromAndIdentifier(securityOrganizationOld.getId().getClient(), securityOrganizationOld.getId().getHistoryFrom(), securityOrganizationOld.getId().getIdentifier());
        if (oldSecurityOrganization.isPresent()) {
            if (!fullSync && !oldSecurityOrganization.get().getLastChange().equals(securityOrganizationOld.getLastChange())) {
                // Nothing to do
                return null;
            }
            newSecurityOrganization = oldSecurityOrganization.get();
        } else {
            newSecurityOrganization = new SecurityOrganization();
        }
        newSecurityOrganization.update(securityOrganizationOld);
        return newSecurityOrganization;
    }
}