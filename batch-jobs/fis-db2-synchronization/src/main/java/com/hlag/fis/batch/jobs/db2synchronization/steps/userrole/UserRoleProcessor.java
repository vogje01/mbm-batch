package com.hlag.fis.batch.jobs.db2synchronization.steps.userrole;

import com.hlag.fis.db.db2.model.UserRoleOld;
import com.hlag.fis.db.db2.repository.FunctionalUnitOldRepository;
import com.hlag.fis.db.db2.repository.RoleFunctionalUnitOldRepository;
import com.hlag.fis.db.db2.repository.UserRoleOldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserRoleProcessor implements ItemProcessor<UserRoleOld, UserRoleOld> {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleProcessor.class);

    @Value("${dbSync.basis.userRole.fullSync}")
    private boolean fullSync;

    private UserRoleOldRepository userRoleOldRepository;

    private FunctionalUnitOldRepository functionalUnitOldRepository;

    private RoleFunctionalUnitOldRepository roleFunctionalUnitOldRepository;

    @Autowired
    public UserRoleProcessor(UserRoleOldRepository userRoleOldRepository, FunctionalUnitOldRepository functionalUnitOldRepository, RoleFunctionalUnitOldRepository roleFunctionalUnitOldRepository) {
        this.userRoleOldRepository = userRoleOldRepository;
        this.functionalUnitOldRepository = functionalUnitOldRepository;
        this.roleFunctionalUnitOldRepository = roleFunctionalUnitOldRepository;
    }

    /**
     * Item processor for the user role model.
     * <p>
     * This will create a new MySQL user role model.
     *
     * @param userRoleOld old DB2 user .
     * @return full developed MySQL user role model.
     */
    @Override
    public UserRoleOld process(UserRoleOld userRoleOld) {
        logger.debug("Processing old user role - " + userRoleOld.toString());
        /*UserRole newUserRole;
        UserRole oldUserRole = userRoleOldRepository.findByEnvironmentAndIdentifier(userRoleOld.getId().getEnvironment(), userRoleOld.getId().getIdentifier());
        if (oldUserRole != null) {
            newUserRole = new UserRole(userRoleOld, oldUserRole.getId());
            if (!fullSync && !oldUserRole.getLastChange().equals(userRoleOld.getLastChange())) {
                // Nothing to do
                return null;
            }
        } else {
            newUserRole = new UserRole(userRoleOld);
        }
        getFunctionalUnitConstraints(newUserRole);*/

        return userRoleOld;
    }
}