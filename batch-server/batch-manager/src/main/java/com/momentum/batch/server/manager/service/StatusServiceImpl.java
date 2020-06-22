package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.dto.JobStatisticDto;
import com.momentum.batch.server.database.domain.projection.JobStatusProjection;
import com.momentum.batch.server.database.repository.JobStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class StatusServiceImpl implements StatusService {

    private final JobStatusRepository jobStatusRepository;

    @Autowired
    public StatusServiceImpl(JobStatusRepository jobStatusRepository) {
        this.jobStatusRepository = jobStatusRepository;
    }

    @Override
    public JobStatisticDto getJobStatistics() {

        JobStatisticDto jobStatisticDto = new JobStatisticDto();
        List<JobStatusProjection> jobStatusProjections = jobStatusRepository.findJobStatus();

        for (JobStatusProjection p : jobStatusProjections) {
            switch (p.getStatus()) {
                case "ABANDONED" -> jobStatisticDto.setAbandonedJobs(p.getValue() != null ? p.getValue() : 0);
                case "COMPLETED" -> jobStatisticDto.setCompletedJobs(p.getValue() != null ? p.getValue() : 0);
                case "FAILED" -> jobStatisticDto.setFailedJobs(p.getValue() != null ? p.getValue() : 0);
                case "STARTED" -> jobStatisticDto.setStartedJobs(p.getValue() != null ? p.getValue() : 0);
                case "STARTING" -> jobStatisticDto.setStartingJobs(p.getValue() != null ? p.getValue() : 0);
                case "STOPPED" -> jobStatisticDto.setStoppedJobs(p.getValue() != null ? p.getValue() : 0);
                case "STOPPING" -> jobStatisticDto.setStoppingJobs(p.getValue() != null ? p.getValue() : 0);
                case "UNKNOWN" -> jobStatisticDto.setUnknownJobs(p.getValue() != null ? p.getValue() : 0);
            }
        }
        jobStatisticDto.setTotalJobs(jobStatisticDto.getStartedJobs() + jobStatisticDto.getCompletedJobs() + jobStatisticDto.getFailedJobs());
        return jobStatisticDto;
    }
}
