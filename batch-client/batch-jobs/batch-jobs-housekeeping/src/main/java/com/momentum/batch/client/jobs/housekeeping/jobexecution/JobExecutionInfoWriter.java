package com.momentum.batch.client.jobs.housekeeping.jobexecution;

import com.momentum.batch.client.jobs.common.writer.MysqlDeleteWriter;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import org.springframework.stereotype.Component;

/**
 * Job execution info delete writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@Component
public class JobExecutionInfoWriter extends MysqlDeleteWriter<JobExecutionInfo> {
}
