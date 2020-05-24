package com.hlag.fis.batch.jobs.housekeeping.job;

import com.hlag.fis.batch.builder.BatchJobBuilder;
import com.hlag.fis.batch.builder.BatchJobRunner;
import com.hlag.fis.batch.jobs.housekeeping.steps.docinstruction.DocumentationInstructionStep;
import com.hlag.fis.batch.jobs.housekeeping.steps.documentationlifecycle.DocumentationLifecycleStep;
import com.hlag.fis.batch.jobs.housekeeping.steps.documentationrequest.DocumentationRequestStep;
import com.hlag.fis.batch.jobs.housekeeping.steps.plannedshipment.PlannedShipmentStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.text.MessageFormat.format;

@Component
public class HouseKeepingDataJob {

    private static final Logger logger = LoggerFactory.getLogger(HouseKeepingDataJob.class);

    private static final String JOB_NAME = "Housekeeping Data";

    private BatchJobRunner batchJobRunner;

    private BatchJobBuilder batchJobBuilder;

    private DocumentationLifecycleStep documentationLifecycleStep;

    private DocumentationInstructionStep documentationInstructionStep;

    private DocumentationRequestStep documentationRequestStep;

    private PlannedShipmentStep plannedShipmentStep;

    @Autowired
    @SuppressWarnings("squid:S00107")
    public HouseKeepingDataJob(
            BatchJobBuilder batchJobBuilder,
            BatchJobRunner batchJobRunner,
            DocumentationLifecycleStep documentationLifecycleStep,
            DocumentationInstructionStep documentationInstructionStep,
            DocumentationRequestStep documentationRequestStep,
            PlannedShipmentStep plannedShipmentStep) {
        this.batchJobBuilder = batchJobBuilder;
        this.batchJobRunner = batchJobRunner;
        this.documentationLifecycleStep = documentationLifecycleStep;
        this.documentationInstructionStep = documentationInstructionStep;
        this.documentationRequestStep = documentationRequestStep;
        this.plannedShipmentStep = plannedShipmentStep;
    }

    @PostConstruct
    public void initialize() {
        logger.info(format("Running job - jobName: {0}", JOB_NAME));
        Job job = houseKeepingJob();
        batchJobRunner.jobName(JOB_NAME)
                .job(job)
                .start();
    }

    /**
     * Create the database synchronization jobs.
     *
     * @return database synchronization jobs.
     */
    public Job houseKeepingJob() {
        logger.info(format("Initializing job - jobName: {0}", JOB_NAME));
        return batchJobBuilder
                .name(JOB_NAME)
                .startStep(documentationRequestStep.houseKeepingDocumentationRequests())
                .nextStep(documentationInstructionStep.houseKeepingDocumentationInstructions())
                .nextStep(documentationLifecycleStep.houseKeepingDocumentationLifecycles())
                .nextStep(plannedShipmentStep.houseKeepingPlannedShipments())
                .build();
    }
}