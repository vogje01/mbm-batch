package com.hlag.fis.batch.jobs.db2synchronization.steps.message;

import com.hlag.fis.db.db2.model.MessageOld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor implements ItemProcessor<MessageOld, MessageOld> {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    @Value("${dbSync.basis.message.fullSync}")
    private boolean fullSync;

    /**
     * Item processor for the message entity.
     * <p>
     * This will create a new normalized MySQL message entity.
     *
     * @param messageOld old DB2 message .
     * @return full developed MySQL message entity.
     */
    public MessageOld process(MessageOld messageOld) {
        logger.debug("Processing old message  - " + messageOld.toString());
        return messageOld;
    }
}