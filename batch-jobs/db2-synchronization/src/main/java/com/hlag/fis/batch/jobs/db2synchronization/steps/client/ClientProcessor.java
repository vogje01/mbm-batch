package com.hlag.fis.batch.jobs.db2synchronization.steps.client;

import com.hlag.fis.db.db2.model.ClientOld;
import com.hlag.fis.db.mysql.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Component
public class ClientProcessor implements ItemProcessor<ClientOld, Client> {

    private static final Logger logger = LoggerFactory.getLogger(ClientProcessor.class);

    private EntityManager mysqlEm;

    @Autowired
    public ClientProcessor(@Qualifier("db2DeveEntityManagerFactory") EntityManager mysqlEm) {
        this.mysqlEm = mysqlEm;
    }

    /**
     * Item processor for the client model.
     * <p>
     * This will create a new MySQL client model.
     *
     * @param clientOld old DB2 client .
     * @return full developed MySQL client model.
     */
    @Override
    public Client process(ClientOld clientOld) {
        logger.debug("Processing old client  - " + clientOld.toString());
        try {
            Client old = getOldUser(clientOld);
            if (!old.getLastChange().equals(clientOld.getLastChange())) {
                return new Client();
            }
        } catch (NoResultException ex) {
            return new Client();
        }
        return null;
    }

    /**
     * Returns a old client by primary key.
     * <p>
     * Primary key is client ID.
     *
     * @param clientOld old client primary key.
     * @return old client with the specified primary key.
     * @throws NoResultException if no client is found with the given primary key.
     */
    private Client getOldUser(ClientOld clientOld) {
        TypedQuery<Client> query = mysqlEm
                .createQuery("select c from Client c where c.idCode = :idCode", Client.class)
                .setParameter("idCode", clientOld.getId().getIdCode());
        return query.getSingleResult();
    }
}