package com.hlag.fis.batch.jobs.db2synchronization.job;

import com.hlag.fis.batch.jobs.db2synchronization.steps.client.ClientStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.clientauthorization.ClientAuthorizationStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.clientrole.ClientRoleStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.documentationinstruction.DocumentationInstructionStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.documentationlifecycle.DocumentationLifecycleStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.documentationrequest.DocumentationRequestStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.functionalunit.FunctionalUnitStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.geohierarchy.GeoHierarchyStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.location.LocationStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.message.MessageStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.organizationplace.OrganizationPlaceStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.plannedshipment.PlannedShipmentStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.securityorganization.SecurityOrganizationStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.transportunitpoint.TransportUnitPointStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.trustworthexclusion.TrustworthExclusionStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.userauthorization.UserAuthorizationStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.userrole.UserRoleStep;
import com.hlag.fis.batch.jobs.db2synchronization.steps.users.UsersStep;
import com.hlag.fis.batch.listener.JobNotificationListener;
import com.hlag.fis.batch.util.NetworkUtils;
import org.apache.logging.log4j.core.layout.ExtendedJsonAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.text.MessageFormat.format;

@Component
public class DatabaseSynchronizationJob implements CommandLineRunner {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSynchronizationJob.class);
    /**
     * In order to get the build version.
     */
    private BuildProperties buildProperties;
    /**
     * Job name
     */
    private static final String JOB_NAME = "Database Synchronization DB2";
    /**
     * Job builder factory
     */
    private JobBuilderFactory jobBuilderFactory;
    /**
     * Job launcher
     */
    private JobLauncher jobLauncher;
    /**
     * Job repository
     */
    private JobRepository jobRepository;
    /**
     * Job notification listener.
     */
    private JobNotificationListener jobNotificationListener;
    /**
     * Task executor for parallel steps.
     */
    private TaskExecutor taskExecutor;
    /**
     * Users synchronization step.
     */
    private UsersStep usersStep;
    /**
     * Clients synchronization step.
     */
    private ClientStep clientStep;
    /**
     * User roles synchronization step.
     */
    private UserRoleStep userRoleStep;
    /**
     * Client roles synchronization step.
     */
    private ClientRoleStep clientRoleStep;
    /**
     * Functional unit synchronization step.
     */
    private FunctionalUnitStep functionalUnitStep;

    private UserAuthorizationStep userAuthorizationStep;

    private ClientAuthorizationStep clientAuthorizationStep;

    private SecurityOrganizationStep securityOrganizationStep;

    private TrustworthExclusionStep trustworthExclusionStep;

    private MessageStep messageStep;

    private OrganizationPlaceStep organizationPlaceStep;

    private LocationStep locationStep;

    private GeoHierarchyStep geoHierarchyStep;

    private PlannedShipmentStep plannedShipmentStep;

    private TransportUnitPointStep transportUnitPointStep;

    private DocumentationRequestStep documentationRequestStep;

    private DocumentationInstructionStep documentationInstructionStep;

    private DocumentationLifecycleStep documentationLifecycleStep;

    boolean started = false;

    @Autowired
    @SuppressWarnings({"squid:S00107", "SpringJavaInjectionPointsAutowiringInspection"})
    public DatabaseSynchronizationJob(
            JobBuilderFactory jobBuilderFactory,
            JobLauncher jobLauncher,
            JobRepository jobRepository,
            JobNotificationListener jobNotificationListener,
            TaskExecutor taskExecutor,
            BuildProperties buildProperties,
            UsersStep usersStep,
            ClientStep clientStep,
            UserRoleStep userRoleStep,
            ClientRoleStep clientRoleStep,
            FunctionalUnitStep functionalUnitStep,
            UserAuthorizationStep userAuthorizationStep,
            ClientAuthorizationStep clientAuthorizationStep,
            SecurityOrganizationStep securityOrganizationStep,
            TrustworthExclusionStep trustworthExclusionStep,
            MessageStep messageStep,
            OrganizationPlaceStep organizationPlaceStep,
            LocationStep locationStep,
            GeoHierarchyStep geoHierarchyStep,
            PlannedShipmentStep plannedShipmentStep,
            TransportUnitPointStep transportUnitPointStep,
            DocumentationRequestStep documentationRequestStep,
            DocumentationInstructionStep documentationInstructionStep,
            DocumentationLifecycleStep documentationLifecycleStep) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.jobNotificationListener = jobNotificationListener;
        this.taskExecutor = taskExecutor;
        this.buildProperties = buildProperties;
        this.usersStep = usersStep;
        this.clientStep = clientStep;
        this.userRoleStep = userRoleStep;
        this.clientRoleStep = clientRoleStep;
        this.userAuthorizationStep = userAuthorizationStep;
        this.clientAuthorizationStep = clientAuthorizationStep;
        this.securityOrganizationStep = securityOrganizationStep;
        this.trustworthExclusionStep = trustworthExclusionStep;
        this.functionalUnitStep = functionalUnitStep;
        this.messageStep = messageStep;
        this.organizationPlaceStep = organizationPlaceStep;
        this.locationStep = locationStep;
        this.geoHierarchyStep = geoHierarchyStep;
        this.plannedShipmentStep = plannedShipmentStep;
        this.transportUnitPointStep = transportUnitPointStep;
        this.documentationRequestStep = documentationRequestStep;
        this.documentationInstructionStep = documentationInstructionStep;
        this.documentationLifecycleStep = documentationLifecycleStep;
    }

    /**
     * Create the database synchronization jobs.
     *
     * @return database synchronization jobs.
     */
    public Job entityJob() {

        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("AllFlow");

        Flow basisFlow = getBasisEntityFlows();
        if (basisFlow != null) {
            addFlow(flowBuilder, basisFlow);
        }
        Flow organizationPlaceFlow = getOrganizationEntityFlows();
        if (organizationPlaceFlow != null) {
            addFlow(flowBuilder, organizationPlaceFlow);
        }
        Flow bookingFlow = getBookingEntityFlows();
        if (bookingFlow != null) {
            addFlow(flowBuilder, bookingFlow);
        }
        Flow documentationFlow = getDocumentationEntityFlows();
        if (documentationFlow != null) {
            addFlow(flowBuilder, documentationFlow);
        }
        return jobBuilderFactory
                .get(JOB_NAME)
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobNotificationListener)
                .start(flowBuilder.build())
                .end()
                .build();
    }

    /**
     * Return a list of basis model flows.
     * <p>
     * The flows will be executed in parallel.
     *
     * @return list of basis steps to be executed in parallel.
     */
    private Flow getBasisEntityFlows() {
        List<Flow> flows = new ArrayList<>();
        if (usersStep.isEntityActive()) {
            flows.add(createFlow("users", usersStep.synchronizeUsers()));
        }
        if (clientStep.isEntityActive()) {
            flows.add(createFlow("client", clientStep.synchronizeClient()));
        }
        if (clientRoleStep.isEntityActive()) {
            flows.add(createFlow("clientRole", clientRoleStep.synchronizeClientRole()));
        }
        if (userRoleStep.isEntityActive()) {
            flows.add(createFlow("userRole", userRoleStep.synchronizeUserRole()));
        }
        if (functionalUnitStep.isEntityActive()) {
            flows.add(createFlow("functionalUnit", functionalUnitStep.synchronizeFunctionUnit()));
        }
        if (userAuthorizationStep.isEntityActive()) {
            flows.add(createFlow("userAuthorization", userAuthorizationStep.synchronizeUserAuthorization()));
        }
        if (clientAuthorizationStep.isEntityActive()) {
            flows.add(createFlow("clientAuthorization", clientAuthorizationStep.synchronizeClientAuthorization()));
        }
        if (securityOrganizationStep.isEntityActive()) {
            flows.add(createFlow("securityOrganization", securityOrganizationStep.synchronizeSecurityOrganization()));
        }
        if (trustworthExclusionStep.isEntityActive()) {
            flows.add(createFlow("trustworthExclusion", trustworthExclusionStep.synchronizeTrustworthExclusion()));
        }
        if (messageStep.isEntityActive()) {
            flows.add(createFlow("messages", messageStep.synchronizeMessage()));
        }

        if (flows.isEmpty()) {
            return null;
        }
        return new FlowBuilder<Flow>("Documentation flow")
                .split(taskExecutor).add(flows.toArray(new Flow[0])).build();
    }

    /**
     * Return a list of organization place model flows.
     * <p>
     * The flows will be executed in parallel.
     *
     * @return list of organization place steps to be executed in parallel.
     */
    private Flow getOrganizationEntityFlows() {
        List<Flow> flows = new ArrayList<>();
        if (organizationPlaceStep.isEntityActive()) {
            flows.add(createFlow("organizationPlace", organizationPlaceStep.synchronizeOrganizationPlace()));
        }
        if (locationStep.isEntityActive()) {
            flows.add(createFlow("location", locationStep.synchronizeLocation()));
        }
        if (geoHierarchyStep.isEntityActive()) {
            flows.add(createFlow("geoHierarchy", geoHierarchyStep.synchronizeGeoHierarchy()));
        }
        if (flows.isEmpty()) {
            return null;
        }
        return new FlowBuilder<Flow>("Organization flows")
                .split(taskExecutor).add(flows.toArray(new Flow[0])).build();
    }

    /**
     * Return a list of shipment booking model flows.
     * <p>
     * The flows will be executed in parallel.
     *
     * @return list of shipment booking steps to be executed in parallel.
     */
    private Flow getBookingEntityFlows() {
        List<Flow> flows = new ArrayList<>();
        if (transportUnitPointStep.isEntityActive()) {
            flows.add(createFlow("transportUnitPoint", transportUnitPointStep.synchronizeTransportUnitPoints()));
        }
        if (plannedShipmentStep.isEntityActive()) {
            flows.add(createFlow("plannedShipment", plannedShipmentStep.synchronizePlannedShipments()));
        }
        if (flows.isEmpty()) {
            return null;
        }
        return new FlowBuilder<Flow>("Booking flows")
                .split(taskExecutor).add(flows.toArray(new Flow[0])).build();
    }

    /**
     * Return a list of shipment documentation model flows.
     * <p>
     * The flows will be executed in parallel.
     *
     * @return list of shipment documentation steps to be executed in parallel.
     */
    private Flow getDocumentationEntityFlows() {
        List<Flow> flows = new ArrayList<>();
        if (documentationRequestStep.isEntityActive()) {
            flows.add(createFlow("documentationRequest", documentationRequestStep.synchronizeDocumentationRequests()));
        }
        if (documentationInstructionStep.isEntityActive()) {
            flows.add(createFlow("documentationInstructionRequest", documentationInstructionStep.synchronizeDocumentationInstructions()));
        }
        if (documentationLifecycleStep.isEntityActive()) {
            flows.add(createFlow("documentationLifecycleRequest", documentationLifecycleStep.synchronizeDocumentationLifecycles()));
        }
        if (flows.isEmpty()) {
            return null;
        }
        int i = 0;
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("Documentation flows");
        for (Flow flow : flows) {
            if (i == 0) {
                flowBuilder.start(flow);
            } else {
                flowBuilder.next(flow);
            }
            i++;
        }
        flowBuilder.end();
        return flowBuilder.build();
    }

    /**
     * Create a new flow object from a step.
     *
     * @param name name of the flow.
     * @param step step to convert to a flow.
     * @return flow object.
     */
    private Flow createFlow(String name, Step step) {
        return new FlowBuilder<Flow>(name).from(step).end();
    }

    private void addFlow(FlowBuilder<Flow> flowBuilder, Flow flow) {
        if (!started) {
            flowBuilder.start(flow);
            started = true;
        } else {
            flowBuilder.next(flow);
        }
    }

    @Override
    public void run(String... args) {
        logger.info(format("Starting batch job - name: {0}", JOB_NAME));
        try {
            Long pid = ProcessHandle.current().pid();
            String uuid = UUID.randomUUID().toString();
            ExtendedJsonAdapter.addMixedFields("pid", pid);
            ExtendedJsonAdapter.addMixedFields("jobName", JOB_NAME);
            ExtendedJsonAdapter.addMixedFields("jobKey", uuid);
            ExtendedJsonAdapter.addMixedFields("version", buildProperties.getVersion());
            JobExecution jobExecution = jobLauncher.run(entityJob(), new JobParametersBuilder()
                    .addLong("shutdown", 1L)
                    .addLong("launchTime", System.currentTimeMillis())
                    .addString("UUID", uuid)
                    .addString("hostName", Objects.requireNonNull(NetworkUtils.getHostName()))
                    .addLong("pid", pid)
                    .toJobParameters());
            ExtendedJsonAdapter.addMixedFields("jobId", jobExecution.getJobId());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            logger.error(e.getMessage(), e);
        }
    }
}