package com.hlag.fis.batch.jobs.db2synchronization.steps.message;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import com.hlag.fis.db.db2.model.MessageOld;
import com.hlag.fis.db.db2.repository.MessageOldRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageStep {

    private static final String STEP_NAME = "Synchronize Messages";

    @Value("${dbSync.basis.message.entityActive}")
    private boolean entityActive;

    @Value("${dbSync.basis.message.chunkSize}")
    private int chunkSize;

    @Value("${dbSync.basis.message.cutOffDays}")
    private int cutOffDays;

    @Value("${dbSync.basis.message.fullSync}")
    private boolean fullSync;

    private BatchStepBuilder<MessageOld, MessageOld> stepBuilder;

    private MessageOldRepository messageOldRepository;

    private MessageReader messageReader;

    private MessageProcessor messageProcessor;

    private MessageWriter messageWriter;

    @Autowired
    public MessageStep(
            BatchStepBuilder<MessageOld, MessageOld> stepBuilder,
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

    public boolean isEntityActive() {
        return entityActive;
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
