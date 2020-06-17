package com.momentum.batch.client.jobs.housekeeping.batchperformance;

import com.momentum.batch.client.jobs.common.writer.MysqlDeleteWriter;
import com.momentum.batch.server.database.domain.BatchPerformance;
import org.springframework.stereotype.Component;

/**
 * Batch performance delete writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@Component
public class BatchPerformanceWriter extends MysqlDeleteWriter<BatchPerformance> {
}
