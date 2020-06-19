package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.AgentDto;
import com.momentum.batch.common.domain.dto.AgentGroupDto;
import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface JobScheduleService {

    PagedModel<JobScheduleDto> findAll(Pageable pageable);

    JobScheduleDto findById(String uuid) throws ResourceNotFoundException;

    JobScheduleDto findByGroupAndName(String groupName, String jobName) throws ResourceNotFoundException;

    JobScheduleDto insertJobSchedule(JobScheduleDto jobScheduleDto);

    JobScheduleDto updateJobSchedule(final String jobScheduleId, JobScheduleDto jobScheduleDto) throws ResourceNotFoundException;

    void deleteJobSchedule(final String jobScheduleId);

    void updateJobDefinition(JobDefinition jobDefinition) throws ResourceNotFoundException;

    PagedModel<AgentDto> getAgents(final String jobScheduleId, Pageable pageable) throws ResourceNotFoundException;

    PagedModel<AgentGroupDto> getAgentGroups(final String jobScheduleId, Pageable pageable) throws ResourceNotFoundException;

    JobScheduleDto addAgent(final String jobScheduleId, final String nodeName) throws ResourceNotFoundException;

    JobScheduleDto removeAgent(final String jobScheduleId, final String agentId) throws ResourceNotFoundException;

    JobScheduleDto addAgentGroup(final String jobScheduleId, final String agentGroupId) throws ResourceNotFoundException;

    JobScheduleDto removeAgentGroup(final String jobScheduleId, final String agentGroupId) throws ResourceNotFoundException;

    JobScheduleDto startJobSchedule(final String jobScheduleId) throws ResourceNotFoundException;
}
