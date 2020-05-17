package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.clientauthorization;

import com.hlag.fis.db.db2.model.ClientAuthorizationOld;
import com.hlag.fis.db.mysql.model.ClientAuthorization;
import com.hlag.fis.db.mysql.model.SecurityOrganization;
import com.hlag.fis.db.mysql.model.Users;
import com.hlag.fis.db.mysql.repository.ClientAuthorizationRepository;
import com.hlag.fis.db.mysql.repository.SecurityOrganizationRepository;
import com.hlag.fis.db.mysql.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.Optional;

@Component
public class ClientAuthorizationProcessor implements ItemProcessor<ClientAuthorizationOld, ClientAuthorization> {

    private static final Logger logger = LoggerFactory.getLogger(ClientAuthorizationProcessor.class);

    @Value("${clientAuthorization.fullSync}")
    private boolean fullSync;

    private UsersRepository usersRepository;

    private ClientAuthorizationRepository clientAuthorizationRepository;

    private SecurityOrganizationRepository securityOrganizationRepository;

    @Autowired
    public ClientAuthorizationProcessor(UsersRepository usersRepository, ClientAuthorizationRepository clientAuthorizationRepository, SecurityOrganizationRepository securityOrganizationRepository) {
        this.usersRepository = usersRepository;
        this.clientAuthorizationRepository = clientAuthorizationRepository;
        this.securityOrganizationRepository = securityOrganizationRepository;
    }

    /**
     * Item processor for the client role ownership model.
     * <p>
     * This will create a new MySQL client role ownership model.
     *
     * @param clientAuthorizationOld old DB2 client role ownership.
     * @return MySQL client role ownership model.
     */
    @Override
    public ClientAuthorization process(ClientAuthorizationOld clientAuthorizationOld) {
        logger.debug("Processing old client role ownership  - " + clientAuthorizationOld.toString());
        ClientAuthorization newClientAuthorization;
        Optional<ClientAuthorization> oldClientAuthorizationOptional = clientAuthorizationRepository
                .findByFullId(
                        clientAuthorizationOld.getId().getIdCode(),
                        clientAuthorizationOld.getId().getHistoryFrom(),
                        clientAuthorizationOld.getId().getUserId(),
                        clientAuthorizationOld.getId().getUserHistoryFrom(),
                        clientAuthorizationOld.getId().getSecuOrgClient(),
                        clientAuthorizationOld.getId().getSecuOrgIdentifier());
        if (oldClientAuthorizationOptional.isPresent()) {
            if (!fullSync && oldClientAuthorizationOptional.get().getLastChange().equals(clientAuthorizationOld.getLastChange())) {
                // Nothing to do
                return null;
            }
            newClientAuthorization = oldClientAuthorizationOptional.get();
        } else {
            newClientAuthorization = new ClientAuthorization();
        }
        newClientAuthorization.update(clientAuthorizationOld);

        getUserConstraint(clientAuthorizationOld, newClientAuthorization);
        getSecurityOrganizationConstraint(clientAuthorizationOld, newClientAuthorization);

        return newClientAuthorization;
    }

    /**
     * Fills in the one to one relationship between client authorization and user.
     *
     * @param clientAuthorizationOld old client authorization primary key.
     * @throws NoResultException if no client authorization is found with the given primary key.
     */
    private void getUserConstraint(ClientAuthorizationOld clientAuthorizationOld, ClientAuthorization clientAuthorization) {
        Optional<Users> user = usersRepository.findByUserIdAndHistoryFrom(clientAuthorizationOld.getId().getUserId(), clientAuthorizationOld.getId().getUserHistoryFrom());
        user.ifPresent(clientAuthorization::setUsers);
    }

    /**
     * Fills in the one to one relationship between client authorization and security organization.
     *
     * @param clientAuthorizationOld old client authorization primary key.
     * @throws NoResultException if no client authorization is found with the given primary key.
     */
    private void getSecurityOrganizationConstraint(ClientAuthorizationOld clientAuthorizationOld, ClientAuthorization clientAuthorization) {
        Optional<SecurityOrganization> securityOrganizationOptional = securityOrganizationRepository.findByClientAndHistoryFromAndIdentifier(
                clientAuthorizationOld.getId().getSecuOrgClient(), clientAuthorizationOld.getId().getSecuOrgHistoryFrom(),
                clientAuthorizationOld.getId().getSecuOrgIdentifier());
        securityOrganizationOptional.ifPresent(clientAuthorization::setSecurityOrganization);
    }
}