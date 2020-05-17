package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.client;

import com.hlag.fis.db.db2.model.ClientOld;
import com.hlag.fis.db.mysql.model.Client;
import com.hlag.fis.db.mysql.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientProcessor implements ItemProcessor<ClientOld, Client> {

    private static final Logger logger = LoggerFactory.getLogger(ClientProcessor.class);

    @Value("${clientRole.fullSync}")
    private boolean fullSync;

    private ClientRepository clientRepository;

    @Autowired
    public ClientProcessor(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
        Client newClient;
        Optional<Client> oldClientOptional = clientRepository.findByIdCode(clientOld.getId().getIdCode());
        if (oldClientOptional.isPresent()) {
            if (!fullSync && oldClientOptional.get().getLastChange().equals(clientOld.getLastChange())) {
                return null;
            }
            newClient = oldClientOptional.get();
        } else {
            newClient = new Client();
        }
        newClient.update(clientOld);
        return newClient;
    }
}