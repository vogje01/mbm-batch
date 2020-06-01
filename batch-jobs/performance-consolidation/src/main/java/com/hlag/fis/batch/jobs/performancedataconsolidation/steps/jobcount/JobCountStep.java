package com.hlag.fis.batch.jobs.performancedataconsolidation.steps.jobcount;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.domain.JobExecutionInfo;
import com.hlag.fis.batch.logging.BatchLogging;
import com.hlag.fis.batch.repository.AgentPerformanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class JobCountStep {

    private static final String STEP_NAME = "Job count";

    @BatchLogging(stepName = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(JobCountStep.class);

    @Value("${consolidation.batch.jobCount.chunkSize}")
    private int chunkSize;

    private AgentPerformanceRepository agentPerformanceRepository;

    private JobCountReader jobCountReader;

    private JobCountProcessor jobCountProcessor;

    private JobCountWriter jobCountWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public JobCountStep(
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            AgentPerformanceRepository agentPerformanceRepository,
            JobCountReader jobCountReader,
            JobCountProcessor jobCountProcessor,
            JobCountWriter jobCountWriter) {
        this.stepBuilder = stepBuilder;
        this.agentPerformanceRepository = agentPerformanceRepository;
        this.jobCountReader = jobCountReader;
        this.jobCountProcessor = jobCountProcessor;
        this.jobCountWriter = jobCountWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step jobCountProcessing() {
        long totalCount = agentPerformanceRepository.countByType(AgentPerformanceType.DAILY);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(jobCountReader.getReader())
                .processor(jobCountProcessor)
                .writer(jobCountWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
