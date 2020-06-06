package com.momentum.batch.client.jobs.housekeeping.jobexecutionlog;

import com.momentum.batch.server.database.domain.JobExecutionLog;
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
