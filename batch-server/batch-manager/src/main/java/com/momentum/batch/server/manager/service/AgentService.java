package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.dto.AgentDto;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface AgentService {

    PagedModel<AgentDto> findAll(Pageable pageable);

    AgentDto findById(String id) throws ResourceNotFoundException;

    PagedModel<JobScheduleDto> findSchedules(String agentId, Pageable pageable);

    PagedModel<AgentDto> findByAgentGroup(String agentGroupId, Pageable pageable);

    AgentDto updateAgent(AgentDto agentDto) throws ResourceNotFoundException;

    void deleteAgent(String agentId);

    AgentDto addAgentGroup(String id, String name) throws ResourceNotFoundException;

    AgentDto removeAgentGroup(String id, String agentGroupId) throws ResourceNotFoundException;

    AgentDto addSchedule(String id, String name) throws ResourceNotFoundException;

    AgentDto removeSchedule(String id, String scheduleId) throws ResourceNotFoundException;

    AgentDto pauseAgent(String agentId) throws ResourceNotFoundException;
}
