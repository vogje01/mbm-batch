package com.hlag.fis.batch.jobs.db2synchronization.steps.documentationlifecycle;

import com.hlag.fis.db.db2.model.DocumentationLifecycleOld;
import com.hlag.fis.db.db2.repository.DocumentationLifecycleOldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Documentation life cycle processor.
 * <p>
 * This processor inserts all the constraints to a documentation life cycles. Currently this are:
 * <ul>
 * <li><b>Planned shipment:</b> the planned shipment is retrieved by shipment number and client.</li>
 * <li><b>Documentation request:</b> the documentation request is retrieved by name and supplement.</li>
 * </ul>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 */
@Component
public class DocumentationLifecycleProcessor implements ItemProcessor<DocumentationLifecycleOld, DocumentationLifecycleOld> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationLifecycleProcessor.class);

    @Value("${dbSync.shipment.documentation.documentationLifecycle.fullSync}")
    private boolean fullSync;

    private DocumentationLifecycleOldRepository documentationLifecycleOldRepository;

    @Autowired
    public DocumentationLifecycleProcessor(DocumentationLifecycleOldRepository documentationLifecycleOldRepository) {
        this.documentationLifecycleOldRepository = documentationLifecycleOldRepository;
    }

    /**
     * Item processor for the documentation life cycle entity.
     * <p>
     * This will create a new normalized MySQL life cycle entity.
     *
     * @param documentationLifecycleOld old DB2 life cycle entity.
     * @return full developed MySQL lifecycle entity.
     */
    @Override
    public DocumentationLifecycleOld process(DocumentationLifecycleOld documentationLifecycleOld) {
        logger.debug("Processing old documentation life cycle - " + documentationLifecycleOld.toString());
        Optional<DocumentationLifecycleOld> oldDocLifecycleOpt = documentationLifecycleOldRepository.findById(documentationLifecycleOld.getId());
        if (oldDocLifecycleOpt.isPresent()) {
            if (fullSync || oldDocLifecycleOpt.get().getLastChange().equals(documentationLifecycleOld.getLastChange())) {
                return documentationLifecycleOld;
            }
        }
        return null;
    }
}