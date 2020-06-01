package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecutionlog;

import com.hlag.fis.batch.domain.JobExecutionLog;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionLogProcessor implements ItemProcessor<JobExecutionLog, JobExecutionLog> {

    @Autowired
    public JobExecutionLogProcessor() {
    }

    @Override
    public JobExecutionLog process(JobExecutionLog jObExecutionLog) {
        return jObExecutionLog;
    }
}
