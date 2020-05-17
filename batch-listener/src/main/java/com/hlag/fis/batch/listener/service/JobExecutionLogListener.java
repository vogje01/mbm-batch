package com.hlag.fis.batch.listener.service;

import com.hlag.fis.batch.domain.JobExecutionLog;
import com.hlag.fis.batch.repository.JobExecutionLogRepository;
import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.text.MessageFormat.format;

@Service
public class JobExecutionLogListener {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionLogListener.class);

    @Value(value = "${kafka.logging.level}")
    private String level;

    private JobExecutionLogRepository jobExecutionLogRepository;

    @Autowired
    public JobExecutionLogListener(JobExecutionLogRepository jobExecutionLogRepository) {
        this.jobExecutionLogRepository = jobExecutionLogRepository;
    }

    @Transactional
    @KafkaListener(topics = "batchJobExecutionLog", containerFactory = "logKafkaListenerContainerFactory")
    public void listen(JobExecutionLog jobExecutionLog) {
        logger.debug(format("Received job log - message: {0}", jobExecutionLog));
        if (jobExecutionLog.getLevel().ordinal() < Level.valueOf(level).intLevel()) {
            jobExecutionLogRepository.save(jobExecutionLog);
        }
    }
}
