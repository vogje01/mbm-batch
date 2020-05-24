package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecutionlog;

import com.hlag.fis.batch.domain.JobExecutionLog;
import com.hlag.fis.batch.writer.MysqlDeleteWriter;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionLogWriter extends MysqlDeleteWriter<JobExecutionLog> {
}
