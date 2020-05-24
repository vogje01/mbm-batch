package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.domain.JobDefinition;
import com.hlag.fis.batch.domain.JobSchedule;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JobScheduleService {

    Page<JobSchedule> allScheduledJobs(Pageable pageable);

    long countAll();

    Optional<JobSchedule> findById(String uuid);

    Optional<JobSchedule> findByGroupAndName(String groupName, String jobName);

    JobSchedule updateJobSchedule(final String jobScheduleId, JobSchedule jobSchedule) throws ResourceNotFoundException;

    void updateJobDefinition(JobDefinition jobDefinition) throws ResourceNotFoundException;

    JobSchedule insertJobSchedule(JobSchedule jobSchedule);

    Page<Agent> getAgents(final String jobScheduleId, Pageable pageable) throws ResourceNotFoundException;

    long countAgents(final String jobScheduleId);

    JobSchedule addAgent(final String jobScheduleId, final String nodeName) throws ResourceNotFoundException;

    JobSchedule removeAgent(final String jobScheduleId, final String agentId) throws ResourceNotFoundException;
}
