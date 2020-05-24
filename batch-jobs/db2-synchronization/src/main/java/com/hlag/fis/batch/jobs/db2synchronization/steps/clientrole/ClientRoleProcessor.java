package com.hlag.fis.batch.jobs.db2synchronization.steps.clientrole;

import com.hlag.fis.db.db2.model.ClientRoleOld;
import com.hlag.fis.db.mysql.model.ClientRole;
import com.hlag.fis.db.mysql.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Component
public class ClientRoleProcessor implements ItemProcessor<ClientRoleOld, ClientRole> {

    private static final Logger logger = LoggerFactory.getLogger(ClientRoleProcessor.class);

    @Value("${dbSync.basis.clientRole.fullSync}")
    private boolean fullSync;

    private EntityManager db2Em;

    @Autowired
    public ClientRoleProcessor(@Qualifier("db2DeveEntityManagerFactory") EntityManager db2Em) {
        this.db2Em = db2Em;
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
        try {
            ClientRole oldClientRole = getOldClientRole(clientRoleOld);
            if (!oldClientRole.getLastChange().equals(clientRoleOld.getLastChange())) {
                return new ClientRole();
            }
            newClientRole = new ClientRole();
        } catch (NoResultException ex) {
            newClientRole = new ClientRole();
        }

        getUserRoleConstraint(clientRoleOld, newClientRole);

        return newClientRole;
    }

    /**
     * Returns a old client role  by primary key.
     * <p>
     * Primary key is client role  ID.
     *
     * @param clientRoleOld old client role  primary key.
     * @return old client role  with the specified primary key.
     * @throws NoResultException if no client role  is found with the given primary key.
     */
    private ClientRole getOldClientRole(ClientRoleOld clientRoleOld) {
        TypedQuery<ClientRole> query = db2Em.createQuery(
                "select c from ClientRole c where c.client = :client and c.relativeNumber = :relativeNumber and c.userRole.environment = :environment "
                        + "and c.userRole.identifier = :identifier", ClientRole.class)
                .setParameter("client", clientRoleOld.getId().getClient())
                .setParameter("relativeNumber", clientRoleOld.getId().getRelativeNumber())
                .setParameter("environment", clientRoleOld.getRoleEnvironment())
                .setParameter("identifier", clientRoleOld.getRoleIdentifier());
        return query.getSingleResult();
    }

    /**
     * Add the one to one relationship from client  to user role. The relationship
     * is mapped by the user role uuid in the client .
     *
     * @param clientRole client
     */
    private void getUserRoleConstraint(ClientRoleOld clientRoleOld, ClientRole clientRole) {
        Query query = db2Em.createQuery("select u from UserRole u where u.environment = :environment and u.identifier = :identifier")
                .setParameter("environment", clientRoleOld.getRoleEnvironment())
                .setParameter("identifier", clientRoleOld.getRoleIdentifier());
        try {
            UserRole userRole = (UserRole) query.getSingleResult();
            if (clientRole.getUserRole() == null || !clientRole.getUserRole().getId().equals(userRole.getId())) {
                clientRole.setUserRole(userRole);
            }
        } catch (NoResultException ex) {
            logger.debug("No corresponding user role found - item: " + clientRole.getId());
        }
    }
}