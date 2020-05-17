package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.userauthorization;

import com.hlag.fis.db.db2.model.UserAuthorizationOld;
import com.hlag.fis.db.mysql.model.ClientRole;
import com.hlag.fis.db.mysql.model.UserAuthorization;
import com.hlag.fis.db.mysql.model.UserRole;
import com.hlag.fis.db.mysql.model.Users;
import com.hlag.fis.db.mysql.repository.ClientRoleRepository;
import com.hlag.fis.db.mysql.repository.UserAuthorizationRepository;
import com.hlag.fis.db.mysql.repository.UserRoleRepository;
import com.hlag.fis.db.mysql.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuthorizationProcessor implements ItemProcessor<UserAuthorizationOld, UserAuthorization> {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthorizationProcessor.class);

    @Value("${userAuthorization.fullSync}")
    private boolean fullSync;

    private UsersRepository usersRepository;

    private UserRoleRepository userRoleRepository;

    private ClientRoleRepository clientRoleRepository;

    private UserAuthorizationRepository userAuthorizationRepository;

    @Autowired
    public UserAuthorizationProcessor(
            UsersRepository usersRepository,
            UserRoleRepository userRoleRepository,
            ClientRoleRepository clientRoleRepository,
            UserAuthorizationRepository userAuthorizationRepository) {
        this.usersRepository = usersRepository;
        this.userRoleRepository = userRoleRepository;
        this.clientRoleRepository = clientRoleRepository;
        this.userAuthorizationRepository = userAuthorizationRepository;
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
    public UserAuthorization process(UserAuthorizationOld userAuthorizationOld) {

        logger.debug("Processing old user authorization  - " + userAuthorizationOld.toString());
        UserAuthorization newUserAuthorization;
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

        return newUserAuthorization;
    }

    /**
     * Add the one to one relationship from user authorization to users. The relationship
     * is mapped by the users_id uuid in the user authorization.
     *
     * @param userAuthorizationOld old user authorization.
     */
    private void getUsersConstraint(UserAuthorizationOld userAuthorizationOld, UserAuthorization userAuthorization) {
        Optional<Users> usersOptional = usersRepository.findByUserIdAndHistoryFrom(userAuthorizationOld.getId().getUserId(), userAuthorizationOld.getId().getHistoryFrom());
        usersOptional.ifPresent(users -> {
            if (userAuthorization.getUsers() == null || !userAuthorization.getUsers().getId().equals(users.getId())) {
                userAuthorization.setUsers(users);
            }
        });
    }

    /**
     * Add the one to one relationship from user authorization to users. The relationship
     * is mapped by the users_id uuid in the user authorization.
     *
     * @param userAuthorization user authorization.
     */
    private void getUserRoleConstraint(UserAuthorizationOld userAuthorizationOld, UserAuthorization userAuthorization) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findByEnvironmentAndIdentifier(
                userAuthorizationOld.getId().getEnvironment(), userAuthorizationOld.getId().getIdentifier());
        userRoleOptional.ifPresent(userRole -> {
            if (userAuthorization.getUserRole() == null || !userAuthorization.getUserRole().getId().equals(userRole.getId())) {
                userAuthorization.setUserRole(userRole);
            }
        });
    }

    /**
     * Add the one to one relationship from user authorization to users. The relationship
     * is mapped by the users_id uuid in the user authorization.
     *
     * @param userAuthorization user authorization.
     */
    private void getClientRoleConstraint(UserAuthorizationOld userAuthorizationOld, UserAuthorization userAuthorization) {
        Optional<ClientRole> clientRoleOptional = clientRoleRepository.findClientRole(userAuthorizationOld.getId().getClientRoleClient(),
                userAuthorizationOld.getId().getClientRoleRelativeNumber(), userAuthorizationOld.getId().getEnvironment(),
                userAuthorizationOld.getId().getIdentifier());
        clientRoleOptional.ifPresent(clientRole -> {
            if (userAuthorization.getClientRole() == null || !userAuthorization.getClientRole().getId().equals(clientRole.getId())) {
                userAuthorization.setClientRole(clientRole);
            }
        });
    }
}