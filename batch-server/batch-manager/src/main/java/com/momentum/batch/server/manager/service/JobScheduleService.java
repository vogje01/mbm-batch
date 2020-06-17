package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JobScheduleService {

    Page<JobSchedule> findAll(Pageable pageable);

    Optional<JobSchedule> findById(String uuid);

    Optional<JobSchedule> findByGroupAndName(String groupName, String jobName);

    JobSchedule insertJobSchedule(JobSchedule jobSchedule);

    JobSchedule updateJobSchedule(final String jobScheduleId, JobSchedule jobSchedule) throws ResourceNotFoundException;

    void deleteJobSchedule(final String jobScheduleId);

    void updateJobDefinition(JobDefinition jobDefinition) throws ResourceNotFoundException;

    Page<Agent> getAgents(final String jobScheduleId, Pageable pageable) throws ResourceNotFoundException;

    Page<AgentGroup> getAgentGroups(final String jobScheduleId, Pageable pageable) throws ResourceNotFoundException;

    JobSchedule addAgent(final String jobScheduleId, final String nodeName) throws ResourceNotFoundException;

    JobSchedule removeAgent(final String jobScheduleId, final String agentId) throws ResourceNotFoundException;

    JobSchedule addAgentGroup(final String jobScheduleId, final String agentGroupId) throws ResourceNotFoundException;

    JobSchedule removeAgentGroup(final String jobScheduleId, final String agentGroupId) throws ResourceNotFoundException;

    JobSchedule startJobSchedule(final String jobScheduleId) throws ResourceNotFoundException;
}
