package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.clientrole;

import com.hlag.fis.db.db2.model.ClientRoleOld;
import com.hlag.fis.db.mysql.model.ClientRole;
import com.hlag.fis.db.mysql.model.UserRole;
import com.hlag.fis.db.mysql.repository.ClientRoleRepository;
import com.hlag.fis.db.mysql.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRoleProcessor implements ItemProcessor<ClientRoleOld, ClientRole> {

    private static final Logger logger = LoggerFactory.getLogger(ClientRoleProcessor.class);

    @Value("${clientRole.fullSync}")
    private boolean fullSync;

    private ClientRoleRepository clientRoleRepository;

    private UserRoleRepository userRoleRepository;

    @Autowired
    public ClientRoleProcessor(ClientRoleRepository clientRoleRepository, UserRoleRepository userRoleRepository) {
        this.clientRoleRepository = clientRoleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    /**
     * Item processor for the client role model.
     * <p>
     * This will create a new MySQL client role model.
     *
     * @param clientRoleOld old DB2 client role .
     * @return MySQL client role model.
     */
    @Override
    public ClientRole process(ClientRoleOld clientRoleOld) {
        logger.debug("Processing old client role   - " + clientRoleOld.toString());
        ClientRole newClientRole;
        Optional<ClientRole> oldClientRoleOptional = clientRoleRepository.findClientRole(clientRoleOld.getId().getClient(), clientRoleOld.getId().getRelativeNumber(), clientRoleOld.getRoleEnvironment(), clientRoleOld.getRoleIdentifier());
        if (oldClientRoleOptional.isPresent()) {
            if (!fullSync && oldClientRoleOptional.get().getLastChange().equals(clientRoleOld.getLastChange())) {
                return null;
            }
            newClientRole = oldClientRoleOptional.get();
        } else {
            newClientRole = new ClientRole();
        }
        newClientRole.update(clientRoleOld);

        getUserRoleConstraint(clientRoleOld, newClientRole);

        return newClientRole;
    }

    /**
     * Add the one to one relationship from client  to user role. The relationship
     * is mapped by the user role uuid in the client .
     *
     * @param clientRole client
     */
    private void getUserRoleConstraint(ClientRoleOld clientRoleOld, ClientRole clientRole) {

        Optional<UserRole> userRoleOptional = userRoleRepository.findByEnvironmentAndIdentifier(clientRoleOld.getRoleEnvironment(), clientRoleOld.getRoleIdentifier());
        if (userRoleOptional.isPresent()) {
            if (clientRole.getUserRole() == null || !clientRole.getUserRole().getId().equals(userRoleOptional.get().getId())) {
                clientRole.setUserRole(userRoleOptional.get());
            }
        }
    }
}