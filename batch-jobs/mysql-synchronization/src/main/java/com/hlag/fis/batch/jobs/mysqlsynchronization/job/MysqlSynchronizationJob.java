package com.hlag.fis.batch.jobs.mysqlsynchronization.job;

import com.hlag.fis.batch.builder.BatchFlowBuilder;
import com.hlag.fis.batch.builder.BatchJobBuilder;
import com.hlag.fis.batch.builder.BatchJobRunner;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.BasisFlows;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.organization.OrganizationFlows;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.ShipmentFlows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.text.MessageFormat.format;

@Component
public class MysqlSynchronizationJob {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MysqlSynchronizationJob.class);
    /**
     * Job name
     */
    private static final String JOB_NAME = "MySQL Database Synchronization";
    /**
     * Batch job runner
     */
    private BatchJobRunner batchJobRunner;
    /**
     * Batch job builder
     */
    private BatchJobBuilder batchJobBuilder;
    /**
     * Basis flows
     */
    private BasisFlows basisFlows;
    /**
     * Organization flows
     */
    private OrganizationFlows organizationFlows;
    /**
     * Shipment flows
     */
    private ShipmentFlows shipmentFlows;

    /**
     * Create the synchronization flow.
     *
     * @param batchJobBuilder   batch job builder.
     * @param batchJobRunner    batch job runner.
     * @param basisFlows        basis flows.
     * @param organizationFlows organization flows.
     * @param shipmentFlows     shipment flows.
     */
    @Autowired
    public MysqlSynchronizationJob(
            BatchJobBuilder batchJobBuilder,
            BatchJobRunner batchJobRunner,
            BasisFlows basisFlows,
            OrganizationFlows organizationFlows,
            ShipmentFlows shipmentFlows) {
        this.batchJobRunner = batchJobRunner;
        this.batchJobBuilder = batchJobBuilder;
        this.basisFlows = basisFlows;
        this.organizationFlows = organizationFlows;
        this.shipmentFlows = shipmentFlows;
    }

    /**
     * Create the database synchronization jobs.
     *
     * @return database synchronization jobs.
     */
    private Job synchronizationJob() {

        BatchFlowBuilder<Flow> batchFlowBuilder = new BatchFlowBuilder<>("AllFlow");

        batchFlowBuilder.flow(basisFlows.getFlows());
        batchFlowBuilder.flow(organizationFlows.getFlows());
        batchFlowBuilder.flow(shipmentFlows.getFlows());
        logger.info(format("Initializing job - jobName: {0}", JOB_NAME));

        return batchJobBuilder
                .name(JOB_NAME)
                .startFlow(batchFlowBuilder.build())
                .end()
                .build();
    }

    @PostConstruct
    private void initialize() {
        logger.info(format("Running job - jobName: {0}", JOB_NAME));
        Job job = synchronizationJob();
        batchJobRunner.jobName(JOB_NAME)
                .job(job)
                .start();
    }
}