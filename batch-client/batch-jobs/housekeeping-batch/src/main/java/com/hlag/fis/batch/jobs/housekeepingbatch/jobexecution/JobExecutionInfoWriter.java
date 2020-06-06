package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecution;

import com.momentum.batch.client.common.job.writer.writer.MysqlDeleteWriter;
import com.momentum.batch.database.domain.JobExecutionInfo;
import org.springframework.stereotype.Component;

/**
 * Job execution info delete writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Component
public class JobExecutionInfoWriter extends MysqlDeleteWriter<JobExecutionInfo> {
}
