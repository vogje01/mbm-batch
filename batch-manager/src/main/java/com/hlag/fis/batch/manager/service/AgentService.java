package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.domain.JobSchedule;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AgentService {

    Page<Agent> findAll(Pageable pageable);

    long countAll();

    long countSchedules(String agentId);

    Page<JobSchedule> getSchedules(String agentId, Pageable pageable);

    List<String> findAllAgentNames();

    Optional<Agent> findById(String id);

    Optional<Agent> findByNodeName(String nodeName);

    Agent updateAgent(Agent agent) throws ResourceNotFoundException;

    void deleteAgent(String agentId);

    Agent addSchedule(String id, String name);

    Agent removeSchedule(String id, String scheduleId);
}
