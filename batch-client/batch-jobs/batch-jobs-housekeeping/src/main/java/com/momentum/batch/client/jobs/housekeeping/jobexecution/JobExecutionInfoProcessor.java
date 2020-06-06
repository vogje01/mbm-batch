package com.momentum.batch.client.jobs.housekeeping.jobexecution;

import com.momentum.batch.server.database.domain.JobExecutionInfo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionInfoProcessor implements ItemProcessor<JobExecutionInfo, JobExecutionInfo> {

    @Autowired
    public JobExecutionInfoProcessor() {
    }

    @Override
    public JobExecutionInfo process(JobExecutionInfo jobExecutionInfo) {
        return jobExecutionInfo;
    }
}
