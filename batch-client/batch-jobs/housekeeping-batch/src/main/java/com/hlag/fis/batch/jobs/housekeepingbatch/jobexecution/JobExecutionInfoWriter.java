package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecution;

import com.hlag.fis.batch.domain.JobExecutionInfo;
import com.hlag.fis.batch.writer.MysqlDeleteWriter;
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
