package com.hlag.fis.batch.builder;

import com.hlag.fis.batch.logging.BatchLoggerJob;
import com.hlag.fis.batch.logging.BatchLogging;
import com.hlag.fis.batch.util.NetworkUtils;
import org.apache.logging.log4j.core.layout.ExtendedJsonAdapter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static com.hlag.fis.batch.util.ExecutionParameter.*;
import static java.text.MessageFormat.format;

/**
 * Batch job runner.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
 * @since 0.0.1
 */
@Component
public class BatchJobRunner {

    @BatchLogging
    private BatchLoggerJob logger;

    private JobLauncher jobLauncher;

    private String jobName = "System";

    private Job job;

    private Long jobPid;

    private String jobUuid;

    private String nodeName;

    private String jobVersion;

    private long gracefulShutdown = 1L;

    private ApplicationArguments applicationArguments;

    @Autowired
    public BatchJobRunner(JobLauncher jobLauncher, ApplicationArguments applicationArguments, BuildProperties buildProperties) {
        this.jobLauncher = jobLauncher;
        this.applicationArguments = applicationArguments;
        this.nodeName = Objects.requireNonNull(NetworkUtils.getHostName());
        this.jobPid = ProcessHandle.current().pid();
        this.jobUuid = UUID.randomUUID().toString();
        this.jobVersion = buildProperties.getVersion();
        logger.setJobName(jobName);
        logger.setJobUuid(jobUuid);
        logger.setJobVersion(jobVersion);
        logger.setClazz(BatchJobRunner.class);
        //logger = BatchLogger.getJobLogger(jobName, jobUuid, jobVersion, BatchJobRunner.class);
    }

    public BatchJobRunner jobName(String jobName) {
        this.jobName = jobName;
        logger.setJobName(jobName);
        return this;
    }

    public BatchJobRunner job(Job job) {
        this.job = job;
        return this;
    }

    public BatchJobRunner gracefulShutdown(boolean gracefulShutdown) {
        this.gracefulShutdown = gracefulShutdown ? 1L : 0L;
        return this;
    }

    public void start() {
        logger.info(format("Starting batch job - jobName: {0}", jobName));
        try {
            jobLauncher.run(job, getJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            logger.error(format("Could not launch job - error: {0}", e.getMessage()), e);
        }
    }

    private JobParameters getJobParameters() {
        long currentTime = System.currentTimeMillis();
        ExtendedJsonAdapter.addMixedFields(JOB_PID_NAME, jobPid);
        ExtendedJsonAdapter.addMixedFields(JOB_NAME_NAME, jobName);
        ExtendedJsonAdapter.addMixedFields(JOB_UUID_NAME, jobUuid);
        ExtendedJsonAdapter.addMixedFields(JOB_VERSION_NAME, jobVersion);

        // Add command line arguments
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
                .addString(JOB_UUID_NAME, jobUuid)
                .addLong(JOB_PID_NAME, jobPid)
                .addLong(JOB_SHUTDOWN_NAME, gracefulShutdown)
                .addLong(JOB_LAUNCH_TIME, currentTime)
                .addString(JOB_NODE_NAME, nodeName)
                .addString(JOB_VERSION_NAME, jobVersion);

        // Add command line arguments
        System.getProperties().forEach((key, value) -> {
            if (((String) value).length() < 250) {
                jobParametersBuilder.addString((String) key, (String) value);
            }
        });
        return jobParametersBuilder.toJobParameters();
    }
}
