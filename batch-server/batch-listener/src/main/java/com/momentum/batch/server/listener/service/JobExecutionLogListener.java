package com.momentum.batch.server.listener.service;

import com.momentum.batch.server.database.domain.JobExecutionLog;
import com.momentum.batch.server.database.repository.JobExecutionLogRepository;
import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
public class JobExecutionLogListener {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionLogListener.class);

    @Value(value = "${kafka.jobLogging.level}")
    private String level;

    private final JobExecutionLogRepository jobExecutionLogRepository;

    @Autowired
    public JobExecutionLogListener(JobExecutionLogRepository jobExecutionLogRepository) {
        this.jobExecutionLogRepository = jobExecutionLogRepository;
    }

   // @Transactional
    @KafkaListener(topics = "batchJobExecutionLog", containerFactory = "logKafkaListenerContainerFactory")
    public void listen(JobExecutionLog jobExecutionLog) {
        logger.debug(format("Received job log - message: {0}", jobExecutionLog));
        if (jobExecutionLog.getLevel().ordinal() < Level.valueOf(level).intLevel()) {
            jobExecutionLogRepository.save(jobExecutionLog);
        }
    }
}
