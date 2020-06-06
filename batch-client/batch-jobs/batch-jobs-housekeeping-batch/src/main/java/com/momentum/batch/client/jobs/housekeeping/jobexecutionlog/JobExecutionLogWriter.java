package com.momentum.batch.client.jobs.housekeeping.jobexecutionlog;

import com.momentum.batch.client.jobs.common.writer.MysqlDeleteWriter;
import com.momentum.batch.database.domain.JobExecutionLog;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionLogWriter extends MysqlDeleteWriter<JobExecutionLog> {
}
