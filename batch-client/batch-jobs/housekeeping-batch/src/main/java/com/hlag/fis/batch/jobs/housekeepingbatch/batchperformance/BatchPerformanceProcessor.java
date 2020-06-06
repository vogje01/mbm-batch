package com.hlag.fis.batch.jobs.housekeepingbatch.batchperformance;

import com.hlag.fis.batch.domain.BatchPerformance;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BatchPerformanceProcessor implements ItemProcessor<BatchPerformance, BatchPerformance> {

    @Override
    public BatchPerformance process(BatchPerformance BatchPerformance) {
        return BatchPerformance;
    }
}
