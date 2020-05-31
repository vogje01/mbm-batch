package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.message;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.MessageOld;
import com.hlag.fis.db.db2.repository.MessageOldRepository;
import com.hlag.fis.db.mysql.model.Message;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Message synchronization step.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Component
public class MessageStep {

    private static final String STEP_NAME = "Synchronize Messages";

    @Value("${message.chunkSize}")
    private int chunkSize;

    @Value("${message.cutOffDays}")
    private int cutOffDays;

    @Value("${message.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<MessageOld, Message> stepBuilder;

    private MessageOldRepository messageOldRepository;

    private MessageReader messageReader;

    private MessageProcessor messageProcessor;

    private MessageWriter messageWriter;

    @Autowired
    public MessageStep(
            BatchStepBuilder<MessageOld, Message> stepBuilder,
            MessageOldRepository messageOldRepository,
            MessageReader messageReader,
            MessageProcessor messageProcessor,
            MessageWriter messageWriter) {
        this.stepBuilder = stepBuilder;
        this.messageOldRepository = messageOldRepository;
        this.messageReader = messageReader;
        this.messageProcessor = messageProcessor;
        this.messageWriter = messageWriter;
    }

    @SuppressWarnings("unchecked")
    public Step synchronizeMessage() {
        long totalCount = fullSync ? messageOldRepository.count() : messageOldRepository.countByLastChange(DateTimeUtils.getCutOff(cutOffDays));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(messageReader.getReader())
                .processor(messageProcessor)
                .writer(messageWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
