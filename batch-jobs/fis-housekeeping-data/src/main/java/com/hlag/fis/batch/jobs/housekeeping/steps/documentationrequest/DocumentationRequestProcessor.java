package com.hlag.fis.batch.jobs.housekeeping.steps.documentationrequest;

import com.hlag.fis.db.mysql.model.DocumentationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DocumentationRequestProcessor implements ItemProcessor<DocumentationRequest, DocumentationRequest> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationRequestProcessor.class);

    @Override
    public DocumentationRequest process(DocumentationRequest documentationRequest) {
        logger.trace("Deleting documentation request - id: " + documentationRequest.getId());
        documentationRequest.removeAllDocumentationInstructions();
        documentationRequest.removeAllDocumentationLifecycles();
        documentationRequest.setPlannedShipment(null);
        return documentationRequest;
    }
}