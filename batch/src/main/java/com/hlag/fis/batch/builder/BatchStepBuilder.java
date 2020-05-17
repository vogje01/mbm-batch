package com.hlag.fis.batch.builder;

import com.hlag.fis.batch.listener.ChunkNotificationListener;
import com.hlag.fis.batch.listener.StepNotificationListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class BatchStepBuilder<I, O> extends StepBuilderFactory {

  private String stepName;

  private int chunkSize = 1000;

  private ItemStreamReader<I> reader;

  private ItemProcessor<I, O> processor;

  private ItemWriter<O> writer;

  private PlatformTransactionManager transactionManager;

  private StepNotificationListener stepNotificationListener;

  private ChunkNotificationListener chunkNotificationListener;

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

  public BatchStepBuilder<I, O> chunkSize(int chunkSize) {
    this.chunkSize = chunkSize;
    return this;
  }

  public BatchStepBuilder<I, O> reader(ItemStreamReader<I> reader) {
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

  public Step build() {
    return get(stepName)
            .listener(stepNotificationListener)
            .transactionManager(transactionManager)
            .<I, O>chunk(chunkSize)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .listener(chunkNotificationListener)
        .build();
  }
}
