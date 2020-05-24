package com.hlag.fis.batch.jobs.db2synchronization.steps.clientauthorization;

import com.hlag.fis.db.db2.model.ClientAuthorizationOld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientAuthorizationProcessor implements ItemProcessor<ClientAuthorizationOld, ClientAuthorizationOld> {

    private static final Logger logger = LoggerFactory.getLogger(ClientAuthorizationProcessor.class);

    @Value("${dbSync.basis.clientAuthorization.fullSync}")
    private boolean fullSync;

    /**
     * Item processor for the client role ownership model.
     * <p>
     * This will create a new MySQL client role ownership model.
     *
     * @param clientAuthorizationOld old DB2 client role ownership.
     * @return MySQL client role ownership model.
     */
    @Override
    public ClientAuthorizationOld process(ClientAuthorizationOld clientAuthorizationOld) {
        logger.debug("Processing old client role ownership  - " + clientAuthorizationOld.toString());
        return clientAuthorizationOld;
    }
}