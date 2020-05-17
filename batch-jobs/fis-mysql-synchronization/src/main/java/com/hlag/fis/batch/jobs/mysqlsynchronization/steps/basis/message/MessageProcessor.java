package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.message;

import com.hlag.fis.db.db2.model.MessageOld;
import com.hlag.fis.db.mysql.model.Message;
import com.hlag.fis.db.mysql.model.MessageSpecification;
import com.hlag.fis.db.mysql.repository.MessageRepository;
import com.hlag.fis.db.mysql.repository.MessageSpecificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Message synchronization processor.
 * <p>
 * Adds the message specification to the message.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
public class MessageProcessor implements ItemProcessor<MessageOld, Message> {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    @Value("${message.fullSync}")
    private boolean fullSync;

    private MessageRepository messageRepository;

    private MessageSpecificationRepository messageSpecificationRepository;

    @Autowired
    public MessageProcessor(MessageRepository messageRepository, MessageSpecificationRepository messageSpecificationRepository) {
        this.messageRepository = messageRepository;
        this.messageSpecificationRepository = messageSpecificationRepository;
    }

    /**
     * Item processor for the message entity.
     * <p>
     * This will create a new normalized MySQL message entity. Adds the OneToOne relationship of the message specification
     * to the message.
     * </p>
     *
     * @param messageOld old DB2 message .
     * @return fully normalized MySQL message entity.
     */
    public Message process(MessageOld messageOld) {
        logger.debug("Processing old message  - " + messageOld.toString());
        Message newMessage;
        Optional<Message> oldMessageOpt = messageRepository.findByClientAndRelativeNumber(messageOld.getId().getClient(), messageOld.getId().getRelativeNumber());
        if (oldMessageOpt.isPresent()) {
            if (!fullSync && oldMessageOpt.get().getLastChange().equals(messageOld.getLastChange())) {
                return null;
            }
            newMessage = oldMessageOpt.get();
        } else {
            newMessage = new Message();
        }
        newMessage.update(messageOld);

        // Add the OneToOne relationship to the corresponding message specification
        getMessageSpecification(messageOld, newMessage);

        return newMessage;
    }

    /**
     * Get the corresponding message specification and adds it to the message.
     *
     * @param messageOld old DB2 message entity.
     * @param newMessage new message entity.
     */
    private void getMessageSpecification(MessageOld messageOld, Message newMessage) {
        Optional<MessageSpecification> messageSpecificationOptional = messageSpecificationRepository.findByClientAndRelativeNumber(messageOld.getMessageSpecificationClient(), messageOld.getMessageSpecificationRelativeNumber());
        messageSpecificationOptional.ifPresent(messageSpecification -> {
            logger.debug("Message specification found - id: " + messageSpecification.getId());
            newMessage.setMessageSpecification(messageSpecification);
        });
    }
}