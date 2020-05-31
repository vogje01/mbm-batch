package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.messagespecification;

import com.hlag.fis.db.db2.model.MessageSpecificationOld;
import com.hlag.fis.db.mysql.model.MessageSpecification;
import com.hlag.fis.db.mysql.repository.MessageSpecificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * MessageSpecification synchronization processor.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
public class MessageSpecificationProcessor implements ItemProcessor<MessageSpecificationOld, MessageSpecification> {

    private static final Logger logger = LoggerFactory.getLogger(MessageSpecificationProcessor.class);

    @Value("${messageSpecification.fullSync}")
    private boolean fullSync;

    private MessageSpecificationRepository messageSpecificationRepository;

    @Autowired
    public MessageSpecificationProcessor(MessageSpecificationRepository messageSpecificationRepository) {
        this.messageSpecificationRepository = messageSpecificationRepository;
    }

    /**
     * Item processor for the messageSpecification entity.
     * <p>
     * This will create a new normalized MySQL messageSpecification entity.
     * </p>
     *
     * @param messageSpecificationOld old DB2 messageSpecification .
     * @return full developed MySQL messageSpecification entity.
     */
    public MessageSpecification process(MessageSpecificationOld messageSpecificationOld) {
        logger.debug("Processing old messageSpecification  - " + messageSpecificationOld.toString());
        MessageSpecification newMessageSpecification;
        Optional<MessageSpecification> oldMessageSpecificationOpt = messageSpecificationRepository.findByClientAndRelativeNumber(messageSpecificationOld.getId().getClient(), messageSpecificationOld.getId().getRelativeNumber());
        if (oldMessageSpecificationOpt.isPresent()) {
            if (!fullSync && oldMessageSpecificationOpt.get().getLastChange().equals(messageSpecificationOld.getLastChange())) {
                return null;
            }
            newMessageSpecification = oldMessageSpecificationOpt.get();
        } else {
            newMessageSpecification = new MessageSpecification();
        }
        newMessageSpecification.update(messageSpecificationOld);
        return newMessageSpecification;
    }

}