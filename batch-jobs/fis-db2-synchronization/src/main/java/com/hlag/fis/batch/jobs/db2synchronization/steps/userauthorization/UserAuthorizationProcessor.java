package com.hlag.fis.batch.jobs.db2synchronization.steps.userauthorization;

import com.hlag.fis.db.db2.model.UserAuthorizationOld;
import com.hlag.fis.db.db2.repository.ClientRoleOldRepository;
import com.hlag.fis.db.db2.repository.UserAuthorizationOldRepository;
import com.hlag.fis.db.db2.repository.UserRoleOldRepository;
import com.hlag.fis.db.db2.repository.UsersOldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserAuthorizationProcessor implements ItemProcessor<UserAuthorizationOld, UserAuthorizationOld> {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthorizationProcessor.class);

    @Value("${dbSync.basis.userAuthorization.fullSync}")
    private boolean fullSync;

    private UsersOldRepository usersOldRepository;

    private UserRoleOldRepository userRoleOldRepository;

    private ClientRoleOldRepository clientRoleOldRepository;

    private UserAuthorizationOldRepository userAuthorizationOldRepository;

    @Autowired
    public UserAuthorizationProcessor(
            UsersOldRepository usersOldRepository,
            UserRoleOldRepository userRoleOldRepository,
            ClientRoleOldRepository clientRoleOldRepository,
            UserAuthorizationOldRepository userAuthorizationOldRepository) {
        this.usersOldRepository = usersOldRepository;
        this.userRoleOldRepository = userRoleOldRepository;
        this.clientRoleOldRepository = clientRoleOldRepository;
        this.userAuthorizationOldRepository = userAuthorizationOldRepository;
    }

    /**
     * Item processor for the user authorization model.
     * <p>
     * This will create a new MySQL user authorization model.
     *
     * @param userAuthorizationOld old DB2 user authorization.
     * @return full developed MySQL user authorization model.
     */
    @Override
    public UserAuthorizationOld process(UserAuthorizationOld userAuthorizationOld) {
        logger.debug("Processing old user authorization  - " + userAuthorizationOld.toString());
/*        UserAuthorization newUserAuthorization;
        UserAuthorization oldUserAuthorization = userAuthorizationRepository.findOldUserAuthorization(userAuthorizationOld.getId().getValidFrom(),
          userAuthorizationOld.getId().getBusinessTaskId(), userAuthorizationOld.getId().getUserId(), userAuthorizationOld.getId().getHistoryFrom(),
          userAuthorizationOld.getId().getClientRoleClient(), userAuthorizationOld.getId().getClientRoleRelativeNumber(),
          userAuthorizationOld.getId().getEnvironment(), userAuthorizationOld.getId().getIdentifier());

        if (oldUserAuthorization != null) {
            newUserAuthorization = new UserAuthorization(userAuthorizationOld, oldUserAuthorization.getId());
            if (!fullSync && !oldUserAuthorization.getLastChange().equals(userAuthorizationOld.getLastChange())) {
                // Nothing to do
                return null;
            }
        } else {
            newUserAuthorization = new UserAuthorization(userAuthorizationOld);
        }

        getUsersConstraint(userAuthorizationOld, newUserAuthorization);
        getUserRoleConstraint(userAuthorizationOld, newUserAuthorization);
        getClientRoleConstraint(userAuthorizationOld, newUserAuthorization);
*/
        return userAuthorizationOld;
    }
}