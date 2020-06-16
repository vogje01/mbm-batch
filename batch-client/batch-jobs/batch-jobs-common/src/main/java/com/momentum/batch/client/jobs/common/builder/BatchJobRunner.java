package com.momentum.batch.client.jobs.common.builder;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.momentum.batch.common.util.ExecutionParameter.*;
import static java.text.MessageFormat.format;

/**
 * Batch job runner.
 * <p>
 * This job runner adds several parameters to the job execution, which are needed be the MBM system.
 * </p>
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.5-RELEASE
 * @since 0.0.1
 */
@Component
public class BatchJobRunner {

    @Value("${agent.nodeName}")
    private String nodeName;

    @Value("${agent.hostName}")
    private String hostName;

    @Value("${job.name}")
    private String jobName;

    @Value("${job.version}")
    private String jobVersion;

    @Value("${job.uuid:#{null}}")
    private String jobUuid;

    @Value("${job.context:#{null}}")
    private String jobContext;

    @Value("${job.launchTime:#{null}}")
    private Long jobLaunchTime;

    private final BatchLogger logger;

    private final JobLauncher jobLauncher;

    private final Long jobPid;

    private Job job;

    @Autowired
    public BatchJobRunner(BatchLogger batchLogger, JobLauncher jobLauncher) {
        this.logger = batchLogger;
        this.jobLauncher = jobLauncher;
        this.jobPid = ProcessHandle.current().pid();
        if (this.jobUuid == null) {
            this.jobUuid = UUID.randomUUID().toString();
        }
        if (this.jobLaunchTime == null) {
            this.jobLaunchTime = System.currentTimeMillis();
        }
    }

    public BatchJobRunner job(Job job) {
        this.job = job;
        return this;
    }

    public void start() {
        logger.setJobName(jobName);
        logger.setJobUuid(jobUuid);
        logger.setJobVersion(jobVersion);
        logger.info(format("Starting batch job - jobName: {0}", jobName));
        try {
            jobLauncher.run(job, getJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            logger.error(format("Could not launch job - error: {0}", e.getMessage()), e);
        }
    }

    /**
     * Adds additional parameters to the job execution parameters list, needed by the MPM system.
     *
     * @return job parameters map.
     */
    private JobParameters getJobParameters() {

        // Add command line arguments
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(HOST_NAME, hostName)
                .addString(NODE_NAME, nodeName)
                .addString(JOB_NAME, jobName)
                .addString(JOB_UUID, jobUuid)
                .addString(JOB_VERSION, jobVersion)
                .addString(JOB_CONTEXT, jobContext)
                .addLong(JOB_PID, jobPid)
                .addLong(JOB_LAUNCH_TIME, jobLaunchTime);

        // Add system environment
        System.getProperties().forEach((key, value) -> {
            if (((String) value).length() < 250) {
                jobParametersBuilder.addString((String) key, (String) value);
            }
        });
        return jobParametersBuilder.toJobParameters();
    }
}
