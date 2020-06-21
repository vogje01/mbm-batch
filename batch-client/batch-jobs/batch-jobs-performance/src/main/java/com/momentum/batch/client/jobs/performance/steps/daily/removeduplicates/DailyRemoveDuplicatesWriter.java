package com.momentum.batch.client.jobs.performance.steps.daily.removeduplicates;

import com.momentum.batch.client.jobs.common.writer.MysqlDeleteWriter;
import com.momentum.batch.server.database.domain.BatchPerformance;
import org.springframework.stereotype.Component;

/**
 * Daily performance remove duplicates writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@Component
public class DailyRemoveDuplicatesWriter extends MysqlDeleteWriter<BatchPerformance> {
}
