package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.userrole;

import com.hlag.fis.db.db2.model.RoleFunctionalUnitOld;
import com.hlag.fis.db.db2.model.UserRoleOld;
import com.hlag.fis.db.db2.repository.RoleFunctionalUnitOldRepository;
import com.hlag.fis.db.mysql.model.FunctionalUnit;
import com.hlag.fis.db.mysql.model.UserRole;
import com.hlag.fis.db.mysql.repository.FunctionalUnitRepository;
import com.hlag.fis.db.mysql.repository.UserRoleRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Component
public class UserRoleProcessor implements ItemProcessor<UserRoleOld, UserRole> {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleProcessor.class);

    @Value("${userRole.fullSync}")
    private boolean fullSync;

    private UserRoleRepository userRoleRepository;

    private FunctionalUnitRepository functionalUnitRepository;

    private RoleFunctionalUnitOldRepository roleFunctionalUnitRepository;

    @Autowired
    public UserRoleProcessor(UserRoleRepository userRoleRepository, FunctionalUnitRepository functionalUnitRepository, RoleFunctionalUnitOldRepository roleFunctionalUnitRepository) {
        this.userRoleRepository = userRoleRepository;
        this.functionalUnitRepository = functionalUnitRepository;
        this.roleFunctionalUnitRepository = roleFunctionalUnitRepository;
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
    public UserRole process(UserRoleOld userRoleOld) {
        logger.debug("Processing old user role - " + userRoleOld.toString());
        UserRole newUserRole;
        Optional<UserRole> oldUserRoleOptional = userRoleRepository.findByEnvironmentAndIdentifier(userRoleOld.getId().getEnvironment(), userRoleOld.getId().getIdentifier());
        if (oldUserRoleOptional.isPresent()) {
            if (!fullSync && oldUserRoleOptional.get().getLastChange().equals(userRoleOld.getLastChange())) {
                // Nothing to do
                return null;
            }
            newUserRole = oldUserRoleOptional.get();
        } else {
            newUserRole = new UserRole();
        }
        newUserRole.update(userRoleOld);

        getFunctionalUnitConstraints(newUserRole);

        return newUserRole;
    }

    /**
     * Returns a functional unit by primary key.
     *
     * @param userRole old message primary key.
     * @throws NoResultException if no functional unit is found with the given primary key.
     */
    private void getFunctionalUnitConstraints(UserRole userRole) {
        List<RoleFunctionalUnitOld> roleFunctionalUnits = roleFunctionalUnitRepository
                .findByEnvironmentAndIdentifier(
                        userRole.getEnvironment(),
                        userRole.getIdentifier());
        if (roleFunctionalUnits != null && !roleFunctionalUnits.isEmpty()) {
            roleFunctionalUnits.forEach(roleFunctionalUnit -> {
                Optional<FunctionalUnit> functionalUnitOptional = functionalUnitRepository.findByEnvironmentAndIdentifier(
                        roleFunctionalUnit.getId().getFunctionalUnitEnvironment(), roleFunctionalUnit.getId().getFunctionalUnitIdentifier());
                functionalUnitOptional.ifPresent(functionalUnit -> {
                    Hibernate.initialize(userRole);
                    userRole.addFunctionalUnit(functionalUnit);
                });
            });
        }
    }
}