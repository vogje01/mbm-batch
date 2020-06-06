package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.domain.AgentGroup;
import com.hlag.fis.batch.domain.JobDefinition;
import com.hlag.fis.batch.domain.JobSchedule;
import com.hlag.fis.batch.domain.dto.JobScheduleDto;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JobScheduleService {

    Page<JobSchedule> findAll(Pageable pageable);

    long countAll();

    long countAgents(final String jobScheduleId);

    long countAgentGroups(final String jobScheduleId);

    Optional<JobSchedule> findById(String uuid);

    Optional<JobSchedule> findByGroupAndName(String groupName, String jobName);

    JobSchedule insertJobSchedule(JobSchedule jobSchedule);

    JobSchedule updateJobSchedule(final String jobScheduleId, JobSchedule jobSchedule) throws ResourceNotFoundException;

    void deleteJobSchedule(final String jobScheduleId);

    void updateJobDefinition(JobDefinition jobDefinition) throws ResourceNotFoundException;

    Page<Agent> getAgents(final String jobScheduleId, Pageable pageable) throws ResourceNotFoundException;

    Page<AgentGroup> getAgentGroups(final String jobScheduleId, Pageable pageable) throws ResourceNotFoundException;

    JobScheduleDto addAgent(final String jobScheduleId, final String nodeName) throws ResourceNotFoundException;

    JobScheduleDto removeAgent(final String jobScheduleId, final String agentId) throws ResourceNotFoundException;

    JobScheduleDto addAgentGroup(final String jobScheduleId, final String agentGroupId) throws ResourceNotFoundException;

    JobScheduleDto removeAgentGroup(final String jobScheduleId, final String agentGroupId) throws ResourceNotFoundException;
}
