package com.momentum.batch.client.agent.scheduler;

import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.momentum.batch.common.util.ExecutionParameter.*;
import static java.text.MessageFormat.format;

/**
 * Batch scheduler task.
 *
 * <p>
 * This class executes the job. Depending on the job type a docker image will be started, or a executable JAR file. The working directory is the directory
 * specified in the job definition, or the system TMP directory. Standard output and standard error are redirected to the agents standard output/error.
 * Therefore the startup logs can be seen on the agent console.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.3
 */
@Component
public class BatchSchedulerTask extends QuartzJobBean {

    @Value("${mbm.agent.hostName}")
    private String hostName;

    @Value("${mbm.agent.nodeName}")
    private String nodeName;

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BatchSchedulerTask.class);
    /**
     * Default working directory
     */
    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    /**
     * Docker network
     */
    @Value("${mbm.agent.docker.network}")
    private String dockerNetwork;
    /**
     * Kafka producer for agent commands.
     */
    private final AgentSchedulerMessageProducer agentSchedulerMessageProducer;
    /**
     * The actual process.
     */
    private Process process;
    /**
     * Process list
     */
    private final Map<String, Process> processList = new HashMap<>();
    /**
     * Random number generator for job name.
     */
    private final Random rand = new Random(System.currentTimeMillis());

    /**
     * Constructor.
     *
     * @param agentSchedulerMessageProducer Kafka scheduler message producer.
     */
    @Autowired
    public BatchSchedulerTask(AgentSchedulerMessageProducer agentSchedulerMessageProducer) {
        this.agentSchedulerMessageProducer = agentSchedulerMessageProducer;
    }

    /**
     * Depending on the job type the method starts the appropriate JAR/DOCKER image.
     *
     * @param jobExecutionContext quartz job execution context.
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        logger.info(format("Executing Job - key: {0}", jobExecutionContext.getJobDetail().getKey()));

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        switch (jobDataMap.getString("job.type")) {
            case "JAR":
                startJarFile(jobDataMap);
                break;
            case "DOCKER":
                startDockerImage(jobDataMap);
                break;
            default:
                break;
        }

        // Send job start
        sendJobExecuted(jobExecutionContext);

        // Clean up process map
        cleanupProcessMap();
    }

    /**
     * Start a simple executable jar file.
     *
     * @param jobDataMap job data map, with parameters.
     */
    @SuppressWarnings("unchecked")
    private void startJarFile(JobDataMap jobDataMap) {
        String jobName = jobDataMap.getString(JOB_NAME);
        String jarFile = jobDataMap.getString(JOB_JAR_FILE);
        String command = jobDataMap.getString(JOB_COMMAND);
        File workingDirectory = jobDataMap.getString(JOB_WORKING_DIRECTORY) != null ? new File(jobDataMap.getString(JOB_WORKING_DIRECTORY)) : new File(TMP_DIR);
        List<String> arguments = (List<String>) jobDataMap.get(JOB_ARGUMENTS);
        List<String> commandList = new ArrayList<>();
        commandList.add(command);
        commandList.addAll(arguments);
        commandList.add("-jar");
        commandList.add(jarFile);
        logger.info(format("Starting job - name: {0} workingDirectory: {1} command: {2} jarFile: {3}", jobName, workingDirectory, command, jarFile));
        try {
            process = new ProcessBuilder(commandList)
                    .directory(workingDirectory)
                    .inheritIO()
                    .start();
            logger.info(format("Job started - jobName: {0} pid: {1} arguments: {2}", jobName, process.pid(), getArgumentsAsString(process.info())));
            processList.put(jobName, process);
        } catch (IOException e) {
            logger.error(format("Could not start job - jarFile: {0} workingDirectory: {1} message: {2}", jarFile, workingDirectory, e.getMessage()), e);
        }
    }

    /**
     * Starts a docker image.
     *
     * @param jobDataMap job data map, with parameters.
     */
    @SuppressWarnings("unchecked")
    private void startDockerImage(JobDataMap jobDataMap) {
        String jobName = jobDataMap.getString(JOB_NAME);
        String dockerImage = jobDataMap.getString(JOB_JAR_FILE);
        String command = jobDataMap.getString(JOB_COMMAND);
        File workingDirectory = jobDataMap.getString(JOB_WORKING_DIRECTORY) != null ? new File(jobDataMap.getString(JOB_WORKING_DIRECTORY)) : new File(TMP_DIR);
        List<String> arguments = (List<String>) jobDataMap.get(JOB_ARGUMENTS);
        List<String> commandList = new ArrayList<>();
        commandList.add(command);
        commandList.add("run");
        commandList.add("--network");
        commandList.add(dockerNetwork);
        commandList.add("--hostname");
        commandList.add(nodeName + '_' + rand.nextInt(65536));
        commandList.add(dockerImage);
        commandList.addAll(arguments);
        logger.info(format("Starting job - name: {0} workingDirectory: {1} command: {2} dockerImage: {3}", jobName, workingDirectory, command, dockerImage));
        try {
            process = new ProcessBuilder(commandList)
                    .directory(workingDirectory)
                    .inheritIO()
                    .start();
            logger.info(format("Docker image started - jobName: {0} pid: {1} arguments: {2}", jobName, process.pid(), getArgumentsAsString(process.info())));
            processList.put(jobName, process);
        } catch (IOException e) {
            logger.error(format("Could not start job - dockerImage: {0} workingDirectory: {1} message: {2}", dockerImage, workingDirectory, e.getMessage()), e);
        }
    }

    public void killAllProcesses() {
        logger.info(format("Start killing all jobs"));
        processList.keySet().forEach(this::killProcess);
    }

    /**
     * Kills a single process.
     *
     * @param jobSchedule job schedule.
     */
    public void killProcess(JobScheduleDto jobSchedule) {
        String jobName = jobSchedule.getJobDefinitionDto().getName();
        logger.info(format("Start killing job - jobName: {0}", jobName));
        if (processList.get(jobName) != null) {
            killProcess(jobName);
        } else {
            logger.warn(format("Job not found - jobName: {0}", jobName));
        }
    }

    /**
     * Kills a process by job name.
     *
     * @param jobName job name.
     */
    public void killProcess(String jobName) {
        Process process = processList.get(jobName);
        if (process.isAlive()) {
            process = process.destroyForcibly();
            logger.info(format("Job killed - jobName: {0} exitValue: {1}", jobName, process.exitValue()));
        }
    }

    /**
     * Returns a string of the process arguments.
     *
     * @param info process handle information.
     * @return string of concatenated arguments.
     */
    private String getArgumentsAsString(ProcessHandle.Info info) {
        Optional<String[]> argumentsOptional = info.arguments();
        if (argumentsOptional.isPresent()) {
            StringBuilder stringBuilder = new StringBuilder();
            String[] arguments = argumentsOptional.get();
            for (String argument : arguments) {
                stringBuilder.append(argument).append(" ");
            }
            return stringBuilder.toString();
        }
        return "No arguments";
    }

    private void cleanupProcessMap() {
        logger.info(format("Starting process list cleanup - count: {0}", processList.size()));
        processList.entrySet().removeIf(p -> !p.getValue().isAlive());
        logger.info(format("Finished process list cleanup - count: {0}", processList.size()));
    }

    /**
     * Job was just launched from the Quartz scheduler.
     * <p>
     * We need to inform the server about the new previous/next fire times.
     * </p>
     *
     * @param jobExecutionContext Quartz job execution context.
     */
    private void sendJobExecuted(JobExecutionContext jobExecutionContext) {

        // Get the trigger
        Trigger trigger = jobExecutionContext.getTrigger();
        logger.info(format("Sending job status - key: {0} last: {1} next: {2}", jobExecutionContext.getJobDetail().getKey(), trigger.getPreviousFireTime(), trigger.getNextFireTime()));

        // Build agent command
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        JobScheduleDto jobScheduleDto = new JobScheduleDto();
        jobScheduleDto.setName(jobDataMap.getString(JOB_SCHEDULE_NAME));
        jobScheduleDto.setId(jobDataMap.getString(JOB_SCHEDULE_UUID));
        AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_EXECUTED);
        agentSchedulerMessageDto.setHostName(hostName);
        agentSchedulerMessageDto.setNodeName(nodeName);
        agentSchedulerMessageDto.setJobScheduleDto(jobScheduleDto);

        // And send it to the server
        agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
    }
}
