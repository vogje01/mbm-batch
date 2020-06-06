package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecution;

import com.hlag.fis.batch.domain.JobExecutionInfo;
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
