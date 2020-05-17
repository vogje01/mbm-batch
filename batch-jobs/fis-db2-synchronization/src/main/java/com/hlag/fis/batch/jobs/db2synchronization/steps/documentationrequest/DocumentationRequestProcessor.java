package com.hlag.fis.batch.jobs.db2synchronization.steps.documentationrequest;

import com.hlag.fis.db.db2.model.DocumentationRequestOld;
import com.hlag.fis.db.db2.repository.DocumentationRequestOldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Documentation requests processor.
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 */
@Component
@EnableJpaRepositories(entityManagerFactoryRef = "db2DeveEntityManagerFactory")
public class DocumentationRequestProcessor implements ItemProcessor<DocumentationRequestOld, DocumentationRequestOld> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationRequestProcessor.class);

    @Value("${dbSync.shipment.documentation.documentationRequest.fullSync}")
    private boolean fullSync;

    private DocumentationRequestOldRepository documentationRequestOldRepository;

    @Autowired
    public DocumentationRequestProcessor(DocumentationRequestOldRepository documentationRequestOldRepository) {
        this.documentationRequestOldRepository = documentationRequestOldRepository;
    }

    /**
     * Item processor for the documentation request model.
     * <p>
     * This will create a new MySQL documentation request model.
     *
     * @param documentationRequestOld old DB2 documentation request.
     * @return MySQL documentation request model.
     */
    @Override
    public DocumentationRequestOld process(DocumentationRequestOld documentationRequestOld) {
        logger.debug("Processing old planned shipment - " + documentationRequestOld.toString());
        Optional<DocumentationRequestOld> oldDocRequestOpt = documentationRequestOldRepository.findById(documentationRequestOld.getId());
        if (oldDocRequestOpt.isPresent()) {
            if (fullSync || !oldDocRequestOpt.get().getLastChange().equals(documentationRequestOld.getLastChange())) {
                return documentationRequestOld;
            }
        }
        return null;
    }
}