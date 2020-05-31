package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.messagespecification;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.MessageSpecificationOld;
import com.hlag.fis.db.db2.repository.MessageSpecificationOldRepository;
import com.hlag.fis.db.mysql.model.MessageSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * MessageSpecification specification synchronization step.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Component
public class MessageSpecificationStep {

    private static final Logger logger = LoggerFactory.getLogger(MessageSpecificationStep.class);

    private static final String STEP_NAME = "Synchronize Message Specifications";

    @Value("${messageSpecification.chunkSize}")
    private int chunkSize;

    @Value("${messageSpecification.cutOffDays}")
    private int cutOffDays;

    @Value("${messageSpecification.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<MessageSpecificationOld, MessageSpecification> stepBuilder;

    private MessageSpecificationOldRepository messageSpecificationOldRepository;

    private MessageSpecificationReader messageSpecificationReader;

    private MessageSpecificationProcessor messageSpecificationProcessor;

    private MessageSpecificationWriter messageSpecificationWriter;

    @Autowired
    public MessageSpecificationStep(
            BatchStepBuilder<MessageSpecificationOld, MessageSpecification> stepBuilder,
            MessageSpecificationOldRepository messageSpecificationOldRepository,
            MessageSpecificationReader messageSpecificationReader,
            MessageSpecificationProcessor messageSpecificationProcessor,
            MessageSpecificationWriter messageSpecificationWriter) {
        this.stepBuilder = stepBuilder;
        this.messageSpecificationOldRepository = messageSpecificationOldRepository;
        this.messageSpecificationReader = messageSpecificationReader;
        this.messageSpecificationProcessor = messageSpecificationProcessor;
        this.messageSpecificationWriter = messageSpecificationWriter;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeMessageSpecification() {
        long totalCount = fullSync ? messageSpecificationOldRepository.count() : messageSpecificationOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        logger.info(format("Starting step - name: {0} totalCount: {1} cutOffDay: {2}", STEP_NAME, totalCount, cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(messageSpecificationReader.getReader())
                .processor(messageSpecificationProcessor)
                .writer(messageSpecificationWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
