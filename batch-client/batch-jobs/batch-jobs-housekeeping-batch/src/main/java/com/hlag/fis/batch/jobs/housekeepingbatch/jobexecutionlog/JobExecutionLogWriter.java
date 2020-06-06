package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecutionlog;

import com.momentum.batch.client.common.job.writer.writer.MysqlDeleteWriter;
import com.momentum.batch.database.domain.JobExecutionLog;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionLogWriter extends MysqlDeleteWriter<JobExecutionLog> {
}
