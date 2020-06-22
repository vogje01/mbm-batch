package com.momentum.batch.client.jobs.common.builder;

import com.momentum.batch.client.jobs.common.listener.ChunkNotificationListener;
import com.momentum.batch.client.jobs.common.listener.StepNotificationListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Batch step builder.
 *
 * <p>
 * The batch step builder extends the normal spring batch step builder. It attaches the necessary
 * notification listener to communicate with the MBM batch management server.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Component
public class BatchStepBuilder<I, O> extends StepBuilderFactory {

    private String stepName;

    private int chunkSize = 1000;

    private Tasklet tasklet;

    private ItemReader<I> reader;

    private ItemProcessor<I, O> processor;

    private ItemWriter<O> writer;

    private final PlatformTransactionManager transactionManager;

    private final StepNotificationListener stepNotificationListener;

    private final ChunkNotificationListener chunkNotificationListener;

    /**
     * Constructor.
     *
     * @param jobRepository             spring batch job repository.
     * @param transactionManager        spring batch transaction manager.
     * @param stepNotificationListener  MBM step notification listener.
     * @param chunkNotificationListener MBM chunk notification listener.
     */
    @Autowired
    private BatchStepBuilder(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            StepNotificationListener stepNotificationListener,
            ChunkNotificationListener chunkNotificationListener) {
        super(jobRepository, transactionManager);
        this.transactionManager = transactionManager;
        this.stepNotificationListener = stepNotificationListener;
        this.chunkNotificationListener = chunkNotificationListener;
    }

    public BatchStepBuilder<I, O> name(String name) {
        this.stepName = name;
        return this;
    }

    public BatchStepBuilder<I, O> tasklet(Tasklet tasklet) {
        this.tasklet = tasklet;
        return this;
    }

    public BatchStepBuilder<I, O> nullTasklet() {
        this.tasklet = new BatchNullTasklet();
        return this;
    }

    public BatchStepBuilder<I, O> chunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
        return this;
    }

    public BatchStepBuilder<I, O> reader(ItemStreamReader<I> reader) {
        this.reader = reader;
        return this;
    }

    public BatchStepBuilder<I, O> reader(ItemReader<I> reader) {
        this.reader = reader;
        return this;
    }

    public BatchStepBuilder<I, O> processor(ItemProcessor<I, O> processor) {
        this.processor = processor;
        return this;
    }

    public BatchStepBuilder<I, O> writer(ItemWriter<O> writer) {
        this.writer = writer;
        return this;
    }

    /**
     * Set the total number of items to process.
     *
     * @param totalCount total number of items to process.
     * @return step builder.
     */
    public BatchStepBuilder<I, O> total(long totalCount) {
        stepNotificationListener.setTotalCount(stepName, totalCount);
        return this;
    }

    /**
     * Build the step.
     *
     * @return spring batch step.
     */
    public Step build() {

        StepBuilder stepBuilder = get(stepName).listener(stepNotificationListener);
        if (tasklet != null) {
            return stepBuilder.tasklet(tasklet).build();
        } else {
            return stepBuilder.transactionManager(transactionManager)
                    .<I, O>chunk(chunkSize)
                    .reader(reader)
                    .processor(processor)
                    .writer(writer)
                    .listener(chunkNotificationListener)
                    .build();
        }
    }
}
