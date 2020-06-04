package com.hlag.fis.batch.jobs.housekeepingbatch.batchperformance;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.writer.MysqlDeleteWriter;
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
