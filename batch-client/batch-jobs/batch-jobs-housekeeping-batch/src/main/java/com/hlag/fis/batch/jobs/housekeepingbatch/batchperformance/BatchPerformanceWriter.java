package com.hlag.fis.batch.jobs.housekeepingbatch.batchperformance;

import com.momentum.batch.client.jobs.common.writer.MysqlDeleteWriter;
import com.momentum.batch.database.domain.BatchPerformance;
import org.springframework.stereotype.Component;

/**
 * Batch performance delete writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Component
public class BatchPerformanceWriter extends MysqlDeleteWriter<BatchPerformance> {
}
