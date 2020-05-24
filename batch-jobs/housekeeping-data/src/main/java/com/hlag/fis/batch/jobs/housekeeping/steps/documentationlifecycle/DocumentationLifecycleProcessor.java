package com.hlag.fis.batch.jobs.housekeeping.steps.documentationlifecycle;

import com.hlag.fis.db.mysql.model.DocumentationLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DocumentationLifecycleProcessor implements ItemProcessor<DocumentationLifecycle, DocumentationLifecycle> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationLifecycleProcessor.class);

    @Override
    public DocumentationLifecycle process(DocumentationLifecycle documentationLifecycle) {
        logger.trace("Deleting documentation life cycle - id: " + documentationLifecycle.getId());
        documentationLifecycle.setDocumentationInstruction(null);
        documentationLifecycle.setDocumentationRequest(null);
        return documentationLifecycle;
    }
}