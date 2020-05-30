package com.hlag.fis.batch.agent.scheduler;

import com.hlag.fis.batch.agent.AgentCommandProducer;
import com.hlag.fis.batch.domain.JobSchedule;
import com.hlag.fis.batch.domain.dto.AgentCommandDto;
import com.hlag.fis.batch.domain.dto.AgentCommandType;
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
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Component
public class BatchSchedulerTask extends QuartzJobBean {
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
    @Value("${agent.docker.network}")
    private String dockerNetwork;
    /**
     * Kafka producer for agent commands.
     */
    private AgentCommandProducer agentCommandProducer;
    /**
     * The actual process.
     */
    private Process process;
    /**
     * Process list
     */
    private Map<String, Process> processList = new HashMap<>();
    /**
     * Random number generator for job name.
     */
    private Random rand = new Random(System.currentTimeMillis());
    /**
     * Host name
     */
    private String hostName;
    /**
     * Node name
     */
    private String nodeName;

    /**
     * Constructor.
     *
     * @param nodeName node name.
     */
    @Autowired
    public BatchSchedulerTask(AgentCommandProducer agentCommandProducer, String hostName, String nodeName) {
        this.agentCommandProducer = agentCommandProducer;
        this.hostName = hostName;
        this.nodeName = nodeName;
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
        switch (jobDataMap.getString("job-type")) {
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
        sendJobStart(jobExecutionContext);

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
        String jobName = jobDataMap.getString("job-name");
        String jarFile = jobDataMap.getString("jar-file");
        String command = jobDataMap.getString("command");
        File workingDirectory = jobDataMap.getString("working-directory") != null ? new File(jobDataMap.getString("working-directory")) : new File(TMP_DIR);
        List<String> arguments = (List<String>) jobDataMap.get("arguments");
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
            logger.info(format("Job started - jobName: {0} pid: {1} arguments: {2}", jobName, process.pid(), getArgumentsAsString(process.info().arguments())));
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
        String jobName = jobDataMap.getString("job-name");
        String dockerImage = jobDataMap.getString("jar-file");
        String command = jobDataMap.getString("command");
        File workingDirectory = jobDataMap.getString("working-directory") != null ? new File(jobDataMap.getString("working-directory")) : new File(TMP_DIR);
        List<String> arguments = (List<String>) jobDataMap.get("arguments");
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
            String argumentString = getArgumentsAsString(process.info().arguments());
            logger.info(format("Docker image started - jobName: {0} pid: {1} arguments: {2}", jobName, process.pid(), argumentString));
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
    public void killProcess(JobSchedule jobSchedule) {
        String jobName = jobSchedule.getJobDefinition().getName();
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
     * @param argumentsOptional optional of the arguments.
     * @return string of concatenated arguments.
     */
    private String getArgumentsAsString(Optional<String[]> argumentsOptional) {
        StringBuilder stringBuilder = new StringBuilder();
        if (argumentsOptional.isPresent()) {
            String[] arguments = argumentsOptional.get();
            for (String argument : arguments) {
                stringBuilder.append(argument).append(" ");
            }
        }
        return stringBuilder.toString();
    }

    private void cleanupProcessMap() {
        logger.info(format("Starting process list cleanup - count: {0}", processList.size()));
        processList.entrySet().removeIf(p -> !p.getValue().isAlive());
        logger.info(format("Finished process list cleanup - count: {0}", processList.size()));
    }

    private void sendJobStart(JobExecutionContext jobExecutionContext) {
        Trigger trigger = jobExecutionContext.getTrigger();
        logger.info(format("Sending job status - key: {0} last: {1} next: {2}", jobExecutionContext.getJobDetail().getKey(), trigger.getPreviousFireTime(), trigger.getNextFireTime()));
        AgentCommandDto agentCommandDto = new AgentCommandDto(AgentCommandType.STATUS);
        agentCommandDto.setHostName(hostName);
        agentCommandDto.setNodeName(nodeName);
        agentCommandDto.setJobName(jobExecutionContext.getJobDetail().getKey().getName());
        agentCommandDto.setGroupName(jobExecutionContext.getJobDetail().getKey().getGroup());
        agentCommandDto.setNextFireTime(trigger.getNextFireTime());
        agentCommandDto.setPreviousFireTime(trigger.getPreviousFireTime());
        agentCommandProducer.sendAgentCommand(agentCommandDto);
    }
}
