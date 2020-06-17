package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.domain.JobScheduleMode;
import com.momentum.batch.common.domain.JobScheduleType;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import com.momentum.batch.server.scheduler.library.LibraryFileWatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * The central job scheduler service, checks the job schedules, which have the type CENTRAL as job scheduler
 * type.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.6
 */
@Service
public class CentralBatchScheduler {

    @Value("${mbm.scheduler.interval}")
    private long interval;

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LibraryFileWatcherService.class);
    /**
     * Job schedule directory
     */
    private final JobScheduleRepository jobScheduleRepository;

    /**
     * Constructor.
     *
     * @param jobScheduleRepository job schedules repository.
     */
    @Autowired
    private CentralBatchScheduler(JobScheduleRepository jobScheduleRepository) {
        this.jobScheduleRepository = jobScheduleRepository;
    }

    @PostConstruct
    public void initialize() {
        logger.info(format("Central job scheduler initialized - interval: {0}", interval));
    }

    @Scheduled(fixedRateString = "${mbm.scheduler.interval}000")
    public void checkSchedules() {
        logger.info(format("Check schedules"));

        // Get job schedule list
        List<JobSchedule> jobScheduleList = jobScheduleRepository.findByType(JobScheduleType.CENTRAL);
        jobScheduleList.forEach(jobSchedule -> {
            if (jobSchedule.getMode() == JobScheduleMode.FIXED) {
                checkFixed(jobSchedule);
            }
        });
    }

    private void checkFixed(JobSchedule jobSchedule) {
        logger.info(format("Checking schedule - name: {0}", jobSchedule.getName()));

    }
}
