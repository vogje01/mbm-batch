package com.momentum.batch.server.listener.service;

import com.momentum.batch.server.database.domain.JobExecutionLog;
import com.momentum.batch.server.database.domain.dto.JobExecutionLogDto;
import com.momentum.batch.server.database.repository.JobExecutionLogRepository;
import org.apache.logging.log4j.Level;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

/**
 * Listener for the job execution logs messages send to the Kafka batchJobExecutionLog queue.
 *
 * @author Jens vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.2
 */
@Service
public class JobExecutionLogListener {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionLogListener.class);

    @Value(value = "${kafka.jobLogging.level}")
    private String level;

    private final JobExecutionLogRepository jobExecutionLogRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public JobExecutionLogListener(JobExecutionLogRepository jobExecutionLogRepository, ModelMapper modelMapper) {
        this.jobExecutionLogRepository = jobExecutionLogRepository;
        this.modelMapper = modelMapper;
    }

    @KafkaListener(topics = "batchJobExecutionLog", containerFactory = "logKafkaListenerContainerFactory")
    public void listen(JobExecutionLogDto jobExecutionLogDto) {
        logger.debug(format("Received job log - nodeName: {0} message: {1}", jobExecutionLogDto.getNodeName(), jobExecutionLogDto.getMessage()));
        if (Level.valueOf(jobExecutionLogDto.getLevel()).intLevel() <= Level.valueOf(level).intLevel()) {
            JobExecutionLog jobExecutionLog = modelMapper.map(jobExecutionLogDto, JobExecutionLog.class);
            jobExecutionLogRepository.save(jobExecutionLog);
        }
    }
}
