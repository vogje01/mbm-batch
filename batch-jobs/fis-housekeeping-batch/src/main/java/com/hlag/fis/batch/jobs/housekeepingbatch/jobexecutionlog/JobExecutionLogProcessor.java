package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecutionlog;

import com.hlag.fis.batch.domain.JobExecutionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionLogProcessor implements ItemProcessor<JobExecutionLog, JobExecutionLog> {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionLogProcessor.class);

    @Autowired
    public JobExecutionLogProcessor() {
    }

    @Override
    public JobExecutionLog process(JobExecutionLog jObExecutionLog) {
        return jObExecutionLog;
    }
}
